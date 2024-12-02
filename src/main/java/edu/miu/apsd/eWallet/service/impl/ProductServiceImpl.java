package edu.miu.apsd.eWallet.service.impl;

import edu.miu.apsd.eWallet.dto.request.ProductRequestDTO;
import edu.miu.apsd.eWallet.dto.response.ProductResponseDTO;
import edu.miu.apsd.eWallet.exception.ResourceNotFoundException;
import edu.miu.apsd.eWallet.mapper.ProductMapper;
import edu.miu.apsd.eWallet.model.Product;
import edu.miu.apsd.eWallet.model.User;
import edu.miu.apsd.eWallet.repository.ProductRepository;
import edu.miu.apsd.eWallet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    @PreAuthorize("hasAuthority('product:list')")
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        size = Math.min(size, 20);
        Page<Product> products = repository.findAll(PageRequest.of(page, size));
        return products.map(mapper::productToProductResponseDTO);
    }

    @Override
    @PreAuthorize("hasAuthority('product:create')")
    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {
        Product product = mapper.productRequestDTOToProduct(productRequestDTO);
        product.setMerchant((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Product savedProduct = repository.save(product);
        return mapper.productToProductResponseDTO(savedProduct);
    }

    @Override
    @PreAuthorize("hasAuthority('product:read')")
    public ProductResponseDTO getProductById(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: %s not found", id));

        return mapper.productToProductResponseDTO(product);
    }

    @Override
    @PreAuthorize("@productServiceImpl.isProductOwner(#id, authentication.name)")
    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        Product foundProduct = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: %s not found", id));

        Product product = mapper.productRequestDTOToProduct(productRequestDTO);
        product.setId(id);
        product.setMerchant(foundProduct.getMerchant());

        Product updatedProduct = repository.save(product);
        return mapper.productToProductResponseDTO(updatedProduct);
    }

    @Override
    @PreAuthorize("@productServiceImpl.isProductOwner(#id, authentication.name)")
    public ProductResponseDTO updateProductPartially(UUID id, ProductRequestDTO productRequestDTO) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: %s not found", id));

        if (productRequestDTO.name() != null)
            product.setName(productRequestDTO.name());

        if (productRequestDTO.description() != null)
            product.setName(productRequestDTO.description());

        if (productRequestDTO.price() != null)
            product.setPrice(productRequestDTO.price());

        if (productRequestDTO.stock() != null)
            product.setStock(productRequestDTO.stock());

        Product savedProduct = repository.save(product);
        return mapper.productToProductResponseDTO(savedProduct);
    }

    @Override
    @PreAuthorize("@productServiceImpl.isProductOwner(#id, authentication.name)")
    public void deleteProduct(UUID id) {
        getProductById(id);
        repository.deleteById(id);
    }


    public boolean isProductOwner(UUID id, String authenticatedUsername) {
        return productRepository.findById(id)
                .map(product -> product.getMerchant().getUsername().equals(authenticatedUsername))
                .orElse(false);
    }
}
