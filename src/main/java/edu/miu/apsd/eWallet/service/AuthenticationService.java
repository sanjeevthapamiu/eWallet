package edu.miu.apsd.eWallet.service;

import edu.miu.apsd.eWallet.dto.request.AuthenticationRequestDTO;
import edu.miu.apsd.eWallet.dto.request.RegisterRequestDTO;
import edu.miu.apsd.eWallet.dto.response.AuthenticationResponseDTO;

public interface AuthenticationService {

    AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO);

    AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO);

}
