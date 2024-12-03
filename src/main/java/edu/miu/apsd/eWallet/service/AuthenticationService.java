package edu.miu.apsd.eWallet.service;

import edu.miu.apsd.eWallet.dto.request.AuthenticationRequestDTO;
import edu.miu.apsd.eWallet.dto.request.RegisterRequestDTO;
import edu.miu.apsd.eWallet.dto.response.AuthenticationResponseDTO;
import edu.miu.apsd.eWallet.model.Balance;
import edu.miu.apsd.eWallet.model.User;
import edu.miu.apsd.eWallet.repository.UserRepository;
import edu.miu.apsd.eWallet.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        User user = new User(
                registerRequestDTO.name(),
                registerRequestDTO.email(),
                passwordEncoder.encode(registerRequestDTO.password()),
                registerRequestDTO.phone(),
                registerRequestDTO.gender(),
                registerRequestDTO.role()
        );

        user.setBalance(new Balance());

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return new AuthenticationResponseDTO(token);
    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.email(),
                        authenticationRequestDTO.password()
                )
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponseDTO(token);
    }

}
