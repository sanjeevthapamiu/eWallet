package edu.miu.apsd.eWallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SaleType type;

    @Column(scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Sale(SaleType type, Product product) {
        this.type = type;
        this.product = product;
    }

    public Sale(SaleType type, User user) {
        this.type = type;
        this.user = user;
    }

    public void addAmount(double amount) {
        this.amount = this.amount.add(BigDecimal.valueOf(amount));
    }
}
