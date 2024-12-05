package edu.miu.apsd.eWallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.apsd.eWallet.dto.request.ProductRequestDTO;
import edu.miu.apsd.eWallet.dto.response.MerchantResponseDTO;
import edu.miu.apsd.eWallet.dto.response.ProductResponseDTO;
import edu.miu.apsd.eWallet.model.Role;
import edu.miu.apsd.eWallet.model.User;
import edu.miu.apsd.eWallet.model.UserGender;
import edu.miu.apsd.eWallet.security.JwtService;
import edu.miu.apsd.eWallet.service.ProductService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.mock.MockType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(ProductController.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    private String customerToken;
    private String merchantToken;
    private User merchant;

    @BeforeEach
    public void setup() {
        // Mock Testing
        customerToken = "Bearer mock-customer-token";
        merchantToken = "Bearer mock-merchant-token";

        merchant = new User("Merchant", "merchant@gmail.com", "password", 123456789L, UserGender.FEMALE, Role.MERCHANT);

        Mockito.when(jwtService.getClaims("mock-customer-token")).thenReturn(Jwts.claims().subject("customer@gmail.com").build());
        Mockito.when(jwtService.getClaims("mock-merchant-token")).thenReturn(Jwts.claims().subject("merchant@gmail.com").build());

        Mockito.when(userDetailsService.loadUserByUsername("customer@gmail.com")).thenReturn(new User("Customer", "customer@gmail.com", "password", 987654321L, UserGender.MALE, Role.CUSTOMER));
        Mockito.when(userDetailsService.loadUserByUsername("merchant@gmail.com")).thenReturn(merchant);
    }

    @Test
    void getAllProducts_ShouldReturnProducts() throws Exception {
        MerchantResponseDTO johnMerchant = new MerchantResponseDTO("johndoe@gmail.com", 123456789L);
        MerchantResponseDTO janeMerchant = new MerchantResponseDTO("janesmith@gmail.com", 9876543221L);
        Page<ProductResponseDTO> productsResponseDTOPage = new PageImpl<>(List.of(
                new ProductResponseDTO(UUID.randomUUID(), "iPhone", "Apple iPhone", 999.99, 15, johnMerchant),
                new ProductResponseDTO(UUID.randomUUID(), "Samsung", "Samsung Galaxy", 888.88, 9, janeMerchant)
        ));

        Mockito.when(productService.getAllProducts(0, 10)).thenReturn(productsResponseDTOPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                .header(HttpHeaders.AUTHORIZATION, customerToken)
                .param("page", "0")
                .param("size", "10")
                )
//                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productsResponseDTOPage))
                );
    }

    @Test
    void saveProduct_ShouldReturnCreatedProduct() throws Exception {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO("iPhone", "Apple iPhone", 999.99, 99);

        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO(merchant.getEmail(), merchant.getPhone());
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(UUID.randomUUID(), productRequestDTO.name(), productRequestDTO.description(), productRequestDTO.price(), productRequestDTO.stock(), merchantResponseDTO);

        Mockito.when(productService.saveProduct(productRequestDTO)).thenReturn(productResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .header(HttpHeaders.AUTHORIZATION, merchantToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productResponseDTO))
                )
//                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponseDTO))
                );
    }

    @Test
    void getProduct_ShouldReturnProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO("johndoe@gmail.com", 123456789L);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(UUID.randomUUID(), "iPhone", "Apple iPhone", 999.99, 15, merchantResponseDTO);

        Mockito.when(productService.getProductById(productId)).thenReturn(productResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{productId}", productId)
                        .header(HttpHeaders.AUTHORIZATION, customerToken)
                )
//                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponseDTO))
                );
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductRequestDTO productRequestDTO = new ProductRequestDTO("iPhone", "Apple iPhone", 999.99, 99);

        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO(merchant.getEmail(), merchant.getPhone());
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(UUID.randomUUID(), productRequestDTO.name(), productRequestDTO.description(), productRequestDTO.price(), productRequestDTO.stock(), merchantResponseDTO);

        Mockito.when(productService.updateProduct(productId, productRequestDTO)).thenReturn(productResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{productId}", productId)
                        .header(HttpHeaders.AUTHORIZATION, merchantToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productResponseDTO))
                )
//                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponseDTO))
                );
    }

    @Test
    void updateProductPartially_ShouldReturnPartiallyUpdatedProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        Map<String, Object> productRequestMap = Map.of("price", 498.88);

        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO(merchant.getEmail(), merchant.getPhone());
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(UUID.randomUUID(), "iPhone", "Apple iPhone", 498.88, 9, merchantResponseDTO);

        Mockito.when(productService.updateProductPartially(Mockito.eq(productId), Mockito.any())).thenReturn(productResponseDTO);

        System.out.println(objectMapper.writeValueAsString(productRequestMap));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/products/{productId}", productId)
                        .header(HttpHeaders.AUTHORIZATION, merchantToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestMap))
                )
//                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponseDTO))
                );
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        UUID productId = UUID.randomUUID();

        Mockito.doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{productId}", productId)
                        .header(HttpHeaders.AUTHORIZATION, merchantToken)
                )
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}