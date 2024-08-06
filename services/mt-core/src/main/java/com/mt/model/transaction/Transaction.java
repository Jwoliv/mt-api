package com.mt.model.transaction;

import com.mt.enums.TypeTransaction;
import com.mt.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate date;
    @NotNull
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;
    private String sender;
    private String note;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
