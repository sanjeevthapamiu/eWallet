package edu.miu.apsd.eWallet.mapper;

import edu.miu.apsd.eWallet.dto.response.BalanceResponseDTO;
import edu.miu.apsd.eWallet.model.Balance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    BalanceResponseDTO balanceToBalanceResponseDTO(Balance balance);

}
