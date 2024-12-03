package edu.miu.apsd.eWallet.service;

import edu.miu.apsd.eWallet.dto.request.DepositRequestDTO;
import edu.miu.apsd.eWallet.dto.request.PurchaseRequestDTO;
import edu.miu.apsd.eWallet.dto.request.SendRequestDTO;
import edu.miu.apsd.eWallet.dto.request.WithdrawRequestDTO;
import edu.miu.apsd.eWallet.dto.response.BalanceResponseDTO;
import edu.miu.apsd.eWallet.dto.response.TransactionResponseDTO;
import org.springframework.data.domain.Page;

public interface TransactionService {

    Page<TransactionResponseDTO> getAll(int page, int size);
    BalanceResponseDTO getBalance();
    TransactionResponseDTO depositMoney(DepositRequestDTO depositRequestDTO);
    TransactionResponseDTO withdrawMoney(WithdrawRequestDTO withdrawRequestDTO);
    TransactionResponseDTO sendMoney(SendRequestDTO sendRequestDTO);
    TransactionResponseDTO purchaseProduct(PurchaseRequestDTO purchaseRequestDTO);

}
