package edu.miu.apsd.eWallet.controller;

import edu.miu.apsd.eWallet.dto.response.SaleProductResponseDTO;
import edu.miu.apsd.eWallet.dto.response.SaleUserResponseDTO;
import edu.miu.apsd.eWallet.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

    @GetMapping
    public ResponseEntity<SaleUserResponseDTO> getUserLevelSale() {
        return ResponseEntity.ok(service.getUserLevelSale());
    }

    @GetMapping("/products")
    public ResponseEntity<List<SaleProductResponseDTO>> getProductLevelSales() {
        return ResponseEntity.ok(service.getProductLevelSales());
    }

}
