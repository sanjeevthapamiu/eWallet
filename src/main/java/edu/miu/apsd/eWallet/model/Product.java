package edu.miu.apsd.eWallet.model;

import edu.miu.apsd.eWallet.exception.InsufficientStockException;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private Double price;
    private Integer stock;

    @OneToOne(cascade = CascadeType.ALL)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User merchant;

    @OneToMany(mappedBy = "product")
    private final List<Transaction> transactions = new ArrayList<>();


    public void addTransaction(Transaction transaction) {
        transaction.setProduct(this);
        transactions.add(transaction);
    }

    public void decrementStock(int stock) {
        if (this.stock < stock)
            throw new InsufficientStockException(String.format("Insufficient stock. Expected %d, but only %d available.", stock, this.stock));

        this.stock -= stock;
    }

    @PrePersist
    public void onCreate() {
        this.setSale(new Sale(SaleType.PRODUCT_SALES, this));
    }

}
