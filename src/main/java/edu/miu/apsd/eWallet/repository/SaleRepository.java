package edu.miu.apsd.eWallet.repository;

import edu.miu.apsd.eWallet.model.Sale;
import edu.miu.apsd.eWallet.model.SaleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findByProduct_Id(UUID productId);

    Optional<Sale> findByUser_Id(UUID userId);

    List<Sale> findByTypeAndProduct_Merchant_Id(SaleType type, UUID userId);
}
