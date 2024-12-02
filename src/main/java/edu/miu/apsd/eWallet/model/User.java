package edu.miu.apsd.eWallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private Long phone;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "merchant")
    private final List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private final List<Transaction> sendTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<Transaction> receiveTransactions = new ArrayList<>();

    public User(String name, String email, String password, Long phone, UserGender gender, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
        this.isEnabled = true;
    }

    public void addProduct(Product product) {
        product.setMerchant(this);
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
