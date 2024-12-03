package edu.miu.apsd.eWallet.model;

import edu.miu.apsd.eWallet.exception.InsufficientFundsException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "balances")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(scale = 2)
    private double income;

    @Column(scale = 2)
    private double expense;

    @OneToOne
    private User user;


    public void addIncome(double income) {
        // this.total += income;
        this.total = this.total.add(BigDecimal.valueOf(income));
        this.income += income;
    }

    public void addExpense(double expense) {
        // expense > total
        if (BigDecimal.valueOf(expense).compareTo(total) > 0)
            throw new InsufficientFundsException();

        // this.total -= expense;
        this.total = this.total.subtract(BigDecimal.valueOf(expense));
        this.expense += expense;
    }

}
