package edu.miu.apsd.eWallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(scale = 2)
    private double amount;

    private TransactionType type;

    private LocalDateTime date;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "transaction_from")
    private String from;

    @Column(name = "transaction_to")
    private String to;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private User receiver;


    @PrePersist
    public void onCreate() {
        date = LocalDateTime.now();
    }

}
