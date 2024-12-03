package edu.miu.apsd.eWallet.repository;

import edu.miu.apsd.eWallet.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findByReceiver_IdOrSender_Id(UUID receiverId, UUID senderId, Pageable pageable);

}
