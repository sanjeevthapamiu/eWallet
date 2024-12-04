package edu.miu.apsd.eWallet.service.impl;

import edu.miu.apsd.eWallet.dto.response.SaleProductResponseDTO;
import edu.miu.apsd.eWallet.dto.response.SaleUserResponseDTO;
import edu.miu.apsd.eWallet.model.Product;
import edu.miu.apsd.eWallet.model.Sale;
import edu.miu.apsd.eWallet.model.SaleType;
import edu.miu.apsd.eWallet.model.User;
import edu.miu.apsd.eWallet.repository.SaleRepository;
import edu.miu.apsd.eWallet.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository repository;

    public SaleUserResponseDTO getUserLevelSale() {
        Sale sale = getAuthenticatedUser().getSale();
        return new SaleUserResponseDTO(sale.getAmount());
    }

    public List<SaleProductResponseDTO> getProductLevelSales() {
        User autheticatedUser = getAuthenticatedUser();

        return repository.findByTypeAndProduct_Merchant_Id(SaleType.PRODUCT_SALES, autheticatedUser.getId()).stream()
                .map(this::saleToSaleProductResponseDTO)
                .collect(Collectors.toList());
    }


    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private SaleProductResponseDTO saleToSaleProductResponseDTO(Sale sale) {
        Product product = sale.getProduct();
        return new SaleProductResponseDTO(product.getId(), product.getName(), sale.getAmount());
    }

}
