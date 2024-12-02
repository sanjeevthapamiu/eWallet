package edu.miu.apsd.eWallet.mapper;

import edu.miu.apsd.eWallet.dto.request.ProductRequestDTO;
import edu.miu.apsd.eWallet.dto.response.ProductResponseDTO;
import edu.miu.apsd.eWallet.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<ProductResponseDTO> productListToProductResponseDTOList(List<Product> products);

    Product productRequestDTOToProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO productToProductResponseDTO(Product product);

}
