package edu.miu.apsd.eWallet.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleProductResponseDTO(
        UUID productId,
        String name,
        BigDecimal totalSales
) {}
