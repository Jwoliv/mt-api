package com.mt.runner;

import com.mt.enums.TypeTransaction;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Category;
import com.mt.model.transaction.Transaction;
import com.mt.repository.AccountRepository;
import com.mt.repository.CategoryRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
public class TransactionRunner implements CommandLineRunner {

    @Setter(onMethod = @__(@Autowired))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__(@Autowired))
    private UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryRepository categoryRepository;
    @Setter(onMethod = @__({@Autowired}))
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
//        this.createAndSaveTransactions();
    }

    private void createAndSaveTransactions() {

        Category category = Category.builder().name("category#1").build();
        Account account = Account.builder().name("account#1").build();

        accountRepository.save(account);
        categoryRepository.save(category);

        Calendar calendar = Calendar.getInstance();
        boolean isExample = true;
        for (int i = 0; i < 20; i++) {
            for (int j = 1; j < 24; j++) {
                Transaction transaction = new Transaction();

                LocalDateTime localDateTime = LocalDateTime.of(2024, 7, j, 0, 0,0);

                transaction.setDate(localDateTime);
                transaction.setAmount(BigDecimal.valueOf(Math.random() * 100));
                transaction.setType(isExample ? TypeTransaction.SPENDING : TypeTransaction.EARNING);
                transaction.setUser(userRepository.findByEmail("aaa1@gmail.com").orElse(null));
                transaction.setCreatedAt(LocalDateTime.now());
                transaction.setCategory(categoryRepository.findByName("category#1").orElse(null));
                transaction.setAccount(accountRepository.findByName("account#1").orElse(null));
                isExample = !isExample;
                transactionRepository.save(transaction);
            }
            // Move to the previous day
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }
}
