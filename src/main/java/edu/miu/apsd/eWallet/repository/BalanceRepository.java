package edu.miu.apsd.eWallet.repository;

import edu.miu.apsd.eWallet.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
