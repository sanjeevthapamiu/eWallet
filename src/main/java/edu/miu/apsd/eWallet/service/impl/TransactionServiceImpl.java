package edu.miu.apsd.eWallet.service.impl;

import edu.miu.apsd.eWallet.dto.request.DepositRequestDTO;
import edu.miu.apsd.eWallet.dto.request.PurchaseRequestDTO;
import edu.miu.apsd.eWallet.dto.request.SendRequestDTO;
import edu.miu.apsd.eWallet.dto.request.WithdrawRequestDTO;
import edu.miu.apsd.eWallet.dto.response.BalanceResponseDTO;
import edu.miu.apsd.eWallet.dto.response.TransactionProductResponseDTO;
import edu.miu.apsd.eWallet.dto.response.TransactionResponseDTO;
import edu.miu.apsd.eWallet.exception.ResourceNotFoundException;
import edu.miu.apsd.eWallet.mapper.BalanceMapper;
import edu.miu.apsd.eWallet.model.*;
import edu.miu.apsd.eWallet.repository.*;
import edu.miu.apsd.eWallet.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final BalanceMapper balanceMapper;

    public Page<TransactionResponseDTO> getAll(int page, int size) {
        UUID authenticatedUserId = getAuthenticatedUser().getId();
        size = Math.min(size, 20);
        Pageable pageable = PageRequest.of(page, size);

        return repository.findByReceiver_IdOrSender_Id(authenticatedUserId, authenticatedUserId, pageable)
                .map(this::transactionTotransactionResponseDTO);
    }

    public BalanceResponseDTO getBalance() {
        Balance balance = getAuthenticatedUser().getBalance();
        return balanceMapper.balanceToBalanceResponseDTO(balance);
    }

    @Transactional
    public TransactionResponseDTO depositMoney(DepositRequestDTO depositRequestDTO) {
        User authenticatedUser = getAuthenticatedUser();

        // Balance
        Balance balance = authenticatedUser.getBalance();
        balance.addIncome(depositRequestDTO.amount());
        balanceRepository.save(balance);

        // Transaction
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(depositRequestDTO.amount());
        transaction.setFrom(depositRequestDTO.bank());
        transaction.setReceiver(authenticatedUser);
        Transaction savedTransaction = repository.save(transaction);
        return transactionTotransactionResponseDTO(savedTransaction);
    }

    @Transactional
    public TransactionResponseDTO withdrawMoney(WithdrawRequestDTO withdrawRequestDTO) {
        User authenticatedUser = getAuthenticatedUser();

        // Balance
        Balance balance = authenticatedUser.getBalance();
        balance.addExpense(withdrawRequestDTO.amount());
        balanceRepository.save(balance);

        // Transaction
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(withdrawRequestDTO.amount());
        transaction.setSender(authenticatedUser);
        transaction.setTo(withdrawRequestDTO.bank());
        Transaction savedTransaction = repository.save(transaction);
        return transactionTotransactionResponseDTO(savedTransaction);
    }

    @Transactional
    public TransactionResponseDTO sendMoney(SendRequestDTO sendRequestDTO) {
        User receiver = userRepository.findByEmail(sendRequestDTO.receiverEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver with email: %s Not Found", sendRequestDTO.receiverEmail()));
        User sender = getAuthenticatedUser();

        // Balance
        Balance senderBalance = sender.getBalance();
        senderBalance.addExpense(sendRequestDTO.amount());
        balanceRepository.save(senderBalance);

        Balance receiverBalance = receiver.getBalance();
        receiverBalance.addIncome(sendRequestDTO.amount());
        balanceRepository.save(receiverBalance);

        // Transaction
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(sendRequestDTO.amount());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        Transaction savedTransaction = repository.save(transaction);
        return transactionTotransactionResponseDTO(savedTransaction);
    }

    @Transactional
    public TransactionResponseDTO purchaseProduct(PurchaseRequestDTO purchaseRequestDTO) {
        Product product = productRepository.findById(purchaseRequestDTO.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: %s Not Found", purchaseRequestDTO.productId()));
        User authenticatedUser = getAuthenticatedUser();
        double totalAmount = product.getPrice() * purchaseRequestDTO.stock();

        // Product
        product.decrementStock(purchaseRequestDTO.stock()); // throws InsufficientStockException
        productRepository.save(product);

        // Balance
        Balance buyerBalance = authenticatedUser.getBalance();
        buyerBalance.addExpense(totalAmount); // throws InsufficientFundsException
        balanceRepository.save(buyerBalance);

        Balance sellerBalance = product.getMerchant().getBalance();
        sellerBalance.addIncome(totalAmount);
        balanceRepository.save(sellerBalance);

        // Sale
        Sale productLevelSale = saleRepository.findByProduct_Id(product.getId())
                .orElseThrow(() -> new RuntimeException(String.format("Sale with productId:%s does not exist", product.getId())));
        productLevelSale.addAmount(totalAmount);

        Sale userLevelSale = saleRepository.findByUser_Id(product.getMerchant().getId())
                .orElseThrow(() -> new RuntimeException(String.format("Sale with Merchant's userId:%s does not exist", authenticatedUser.getId())));
        userLevelSale.addAmount(totalAmount);

        saleRepository.saveAll(List.of(productLevelSale, userLevelSale));

        // Transaction
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.PURCHASE);
        transaction.setAmount(totalAmount);
        transaction.setProductQuantity(purchaseRequestDTO.stock());
        transaction.setProduct(product);
        transaction.setSender(authenticatedUser);
        transaction.setReceiver(product.getMerchant());
        Transaction savedTransaction = repository.save(transaction);
        return transactionTotransactionResponseDTO(savedTransaction);
    }


    private TransactionResponseDTO transactionTotransactionResponseDTO(Transaction transaction) {
        String from = transaction.getFrom();
        String to = transaction.getTo();

        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            to = transaction.getReceiver().getName();
        } else if (transaction.getType().equals(TransactionType.WITHDRAW)) {
            from = transaction.getSender().getName();
        } else if (transaction.getType().equals(TransactionType.TRANSFER) || transaction.getType().equals(TransactionType.PURCHASE)) {
            from = transaction.getSender().getName();
            to = transaction.getReceiver().getName();
        }

        Product product = transaction.getProduct();
        TransactionProductResponseDTO productResponseDTO = (product == null) ? null
                : new TransactionProductResponseDTO(product.getId(), product.getName(), transaction.getProductQuantity(), transaction.getAmount());

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                from,
                to,
                productResponseDTO
        );
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
