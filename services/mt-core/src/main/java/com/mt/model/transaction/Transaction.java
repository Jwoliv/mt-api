package com.mt.model.transaction;

import com.mt.enums.TypeTransaction;
import com.mt.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private String sender;
    private String note;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
