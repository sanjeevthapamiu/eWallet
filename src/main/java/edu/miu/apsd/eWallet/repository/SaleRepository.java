package edu.miu.apsd.eWallet.repository;

import edu.miu.apsd.eWallet.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findByProduct_Id(UUID productId);

    Optional<Sale> findByUser_Id(UUID userId);
}
