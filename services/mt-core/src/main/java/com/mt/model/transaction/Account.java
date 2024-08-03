package com.mt.model.transaction;

import com.mt.enums.TypeTransaction;
import com.mt.model.User;
import com.mt.request.ChangeTransactionRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal spendMoney;
    private BigDecimal earnMoney;
    private BigDecimal currentBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "receiverAccount")
    private List<Transaction> transferTransactions;

    public void changeBalanceByUsual(BigDecimal amount, TypeTransaction type) {
        switch (type) {
            case EARNING -> {
                this.currentBalance = currentBalance.add(amount);
                this.earnMoney = earnMoney.add(amount);
            }
            case SPENDING -> {
                this.currentBalance = currentBalance.subtract(amount);
                this.spendMoney = this.spendMoney.add(amount);
            }
        }
    }

    public void changeBalanceByTransfer(BigDecimal amount, Account receiverAccount) {
        this.currentBalance = currentBalance.subtract(amount);
        receiverAccount.setCurrentBalance(receiverAccount.getCurrentBalance().add(amount));
    }
}
