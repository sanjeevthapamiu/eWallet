package edu.miu.apsd.eWallet.service;

import edu.miu.apsd.eWallet.dto.request.ProductRequestDTO;
import edu.miu.apsd.eWallet.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {
    Page<ProductResponseDTO> getAllProducts(int page, int size);
    ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO getProductById(UUID id);
    ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProductPartially(UUID id, ProductRequestDTO productRequestDTO);
    void deleteProduct(UUID id);
}

