package edu.miu.apsd.eWallet.controller;

import edu.miu.apsd.eWallet.dto.response.SuccessResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping
    public ResponseEntity<SuccessResponseDTO> getWelcomeMessage() {
        return ResponseEntity.ok(SuccessResponseDTO.ok("Welcome to eWallet API"));
    }

}
