package edu.miu.apsd.eWallet.dto.response;

import java.math.BigDecimal;

public record SaleUserResponseDTO (
    BigDecimal totalSales
) {}
