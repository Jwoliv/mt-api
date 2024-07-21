package com.mt.runner;

import com.mt.enums.TypeTransaction;
import com.mt.model.transaction.Transaction;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;

@Service
public class TransactionRunner implements CommandLineRunner {

    @Setter(onMethod = @__(@Autowired))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__(@Autowired))
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        this.createAndSaveTransactions();
    }

    private void createAndSaveTransactions() {
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Transaction transaction = new Transaction();
                transaction.setDate(calendar.getTime());
                transaction.setAmount(BigDecimal.valueOf(Math.random() * 100));
                transaction.setType(j % 2 == 0 ? TypeTransaction.SPENDING : TypeTransaction.EARNING);
                transaction.setUser(userRepository.findByEmail("aaa1@gmail.com").orElse(null));
                transaction.setCreatedAt(calendar.getTime());
                transactionRepository.save(transaction);
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }
}
