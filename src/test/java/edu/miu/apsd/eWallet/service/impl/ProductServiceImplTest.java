//package edu.miu.apsd.eWallet.service.impl;
//
//import edu.miu.apsd.eWallet.dto.request.ProductRequestDTO;
//import edu.miu.apsd.eWallet.dto.response.MerchantResponseDTO;
//import edu.miu.apsd.eWallet.dto.response.ProductResponseDTO;
//import edu.miu.apsd.eWallet.mapper.ProductMapper;
//import edu.miu.apsd.eWallet.model.Product;
//import edu.miu.apsd.eWallet.model.Role;
//import edu.miu.apsd.eWallet.model.User;
//import edu.miu.apsd.eWallet.model.UserGender;
//import edu.miu.apsd.eWallet.repository.ProductRepository;
//import edu.miu.apsd.eWallet.repository.UserRepository;
//import edu.miu.apsd.eWallet.service.ProductService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.parameters.P;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//class ProductServiceImplTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private ProductMapper productMapper;
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//
//    private User authenticatedUser;
//
//    @BeforeEach
//    void setUp() {
//        authenticatedUser = new User("Merchant", "merchant@gmail.com", "password", 123456789L, UserGender.MALE, Role.MERCHANT);
//
////        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
////                .thenReturn(authenticatedUser);
//
//
//        // Mock the Authentication object
//        Authentication authentication = Mockito.mock(Authentication.class);
//        Mockito.when(authentication.getPrincipal()).thenReturn(authenticatedUser);
//
//        // Mock the SecurityContext
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//
//        // Set the mocked SecurityContext
//        SecurityContextHolder.setContext(securityContext);
////
////        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(authenticatedUser);
//    }
//
//    @Test
//    void getAllProducts() {
//
//    }
//
//    @Test
//    void saveProduct() {
//        Product product = new Product(UUID.randomUUID(), "iPhone", "Apple", 999.99, 12, null, null);
//        ProductRequestDTO productRequestDTO = new ProductRequestDTO("iPhone", "Apple", 999.99, 12);
//        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO(authenticatedUser.getEmail(), authenticatedUser.getPhone());
//        ProductResponseDTO expectedProductResponseDTO = new ProductResponseDTO(UUID.randomUUID(), "iPhone", "Apple", 999.99, 12, merchantResponseDTO);
//
//        Mockito.when(productRepository.save(product)).thenReturn(product);
//
//        ProductResponseDTO productResponseDTO = productService.saveProduct(productRequestDTO);
//
//        assertEquals(expectedProductResponseDTO, productResponseDTO);
//    }
//
//    @Test
//    void getProductById() {
//    }
//
//    @Test
//    void updateProduct() {
//    }
//
//    @Test
//    void updateProductPartially() {
//    }
//
//    @Test
//    void deleteProduct() {
//    }
//}