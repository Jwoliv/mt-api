package com.mt.model.transaction;

import com.mt.model.User;
import com.mt.request.ChangeTransactionRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.mt.enums.TypeTransaction.TRANSFER;

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

    public void changeCurrentBalance(ChangeTransactionRequest request) {
        switch (request.getType()) {
            case EARNING -> {
                this.currentBalance = currentBalance.add(request.getAmount());
                this.earnMoney = earnMoney.add(request.getAmount());
            }
            case SPENDING -> {
                this.currentBalance = currentBalance.subtract(request.getAmount());
                this.spendMoney = this.spendMoney.add(request.getAmount());
            }
        }
    }

    public void changeTransferTransactions(ChangeTransactionRequest request, Account receiverAccount) {
        if (Objects.equals(request.getType(), TRANSFER)) {
            this.currentBalance = currentBalance.subtract(request.getAmount());
            receiverAccount.setCurrentBalance(receiverAccount.getCurrentBalance().add(request.getAmount()));
        }
    }
}
