package edu.miu.apsd.eWallet.controller;

import edu.miu.apsd.eWallet.dto.request.DepositRequestDTO;
import edu.miu.apsd.eWallet.dto.request.PurchaseRequestDTO;
import edu.miu.apsd.eWallet.dto.request.SendRequestDTO;
import edu.miu.apsd.eWallet.dto.request.WithdrawRequestDTO;
import edu.miu.apsd.eWallet.dto.response.BalanceResponseDTO;
import edu.miu.apsd.eWallet.dto.response.TransactionResponseDTO;
import edu.miu.apsd.eWallet.service.TransactionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance() {
        return ResponseEntity.ok(service.getBalance());
    }

    @PostMapping("/deposit")
    @Transactional
    public ResponseEntity<TransactionResponseDTO> depositMoney(@Valid @RequestBody DepositRequestDTO depositRequestDTO) {
        return ResponseEntity.ok(service.depositMoney(depositRequestDTO));
    }

    @PostMapping("/withdraw")
    @Transactional
    public ResponseEntity<TransactionResponseDTO> withdrawMoney(@Valid @RequestBody WithdrawRequestDTO withdrawRequestDTO) {
        return ResponseEntity.ok(service.withdrawMoney(withdrawRequestDTO));
    }

    @PostMapping("/send")
    @Transactional
    public ResponseEntity<TransactionResponseDTO> sendMoney(@Valid @RequestBody SendRequestDTO sendRequestDTO) {
        return ResponseEntity.ok(service.sendMoney(sendRequestDTO));
    }

    @PostMapping("/purchase")
    @Transactional
    public ResponseEntity<TransactionResponseDTO> purchaseProduct(@Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        return ResponseEntity.ok(service.purchaseProduct(purchaseRequestDTO));
    }

}
