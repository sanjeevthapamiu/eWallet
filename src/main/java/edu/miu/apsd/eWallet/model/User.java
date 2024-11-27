package edu.miu.apsd.eWallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String password;
    private Long phone;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    private String authorities;

//    private UserGender gender;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user")
    private final List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private final List<Transaction> sendTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<Transaction> receiveTransactions = new ArrayList<>();


    public void addProduct(Product product) {
        product.setUser(this);
        products.add(product);
    }

    public void addSendTransaction(Transaction transaction) {
        transaction.setReceiver(this);
        sendTransactions.add(transaction);
    }

    public void addReceiveTransaction(Transaction transaction) {
        transaction.setSender(this);
        receiveTransactions.add(transaction);
    }
}
