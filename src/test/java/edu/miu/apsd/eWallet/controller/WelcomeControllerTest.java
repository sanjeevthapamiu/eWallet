package edu.miu.apsd.eWallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.apsd.eWallet.dto.response.SuccessResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


// @WebMvcTest(WelcomeController.class)
@SpringBootTest
@AutoConfigureMockMvc
class WelcomeControllerTest {

    @Autowired
    private MockMvc mockMvc; // test the controller without actually starting the server

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getWelcomeMessage() throws Exception {
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.ok("Welcome to eWallet API");

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(successResponseDTO))
                );
    }
}
