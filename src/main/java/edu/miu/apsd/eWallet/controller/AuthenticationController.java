package edu.miu.apsd.eWallet.controller;

import edu.miu.apsd.eWallet.dto.request.AuthenticationRequestDTO;
import edu.miu.apsd.eWallet.dto.request.RegisterRequestDTO;
import edu.miu.apsd.eWallet.dto.response.AuthenticationResponseDTO;
import edu.miu.apsd.eWallet.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.register(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequestDTO));
    }

}
