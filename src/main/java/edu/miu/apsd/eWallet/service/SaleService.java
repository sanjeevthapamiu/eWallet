package edu.miu.apsd.eWallet.service;

import edu.miu.apsd.eWallet.dto.response.SaleProductResponseDTO;
import edu.miu.apsd.eWallet.dto.response.SaleUserResponseDTO;

import java.util.List;

public interface SaleService {

    SaleUserResponseDTO getUserLevelSale();

    List<SaleProductResponseDTO> getProductLevelSales();

}
