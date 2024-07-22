package com.mt.runner;

import com.mt.enums.TypeTransaction;
import com.mt.model.DailyAmountReport;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Category;
import com.mt.model.transaction.Transaction;
import com.mt.repository.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;

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
    @Setter(onMethod = @__({@Autowired}))
    private DailyAmountReportRepository amountReportRepository;

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
        BigDecimal initAmount = new BigDecimal("1000.00");
        Integer day = 1;
        Integer month = 6;
        boolean increaseMonth = true;
        for (int i = 1; i < 70; i++) {
            if (isExample) {
                initAmount = initAmount.add(new BigDecimal(i));
            } else {
                initAmount = initAmount.subtract(new BigDecimal(i));
            }

            if (i > 30) {
                day = i % 30;
                if (day == 0) {
                    day++;
                }
                if (increaseMonth) {
                    month++;
                    increaseMonth = false;
                }
            } else {
                day = i;
            }

            LocalDateTime localDateTime = LocalDateTime.of(2024, month, day, 0, 0,0);
            DailyAmountReport amountReport = DailyAmountReport.builder()
                    .amount(initAmount)
                    .date(localDateTime)
                    .user(userRepository.findByEmail("aaa1@gmail.com").orElse(null))
                    .build();
            isExample = !isExample;

            amountReportRepository.save(amountReport);


            for (int j = 1; j < 24; j++) {
                Transaction transaction = new Transaction();

                localDateTime = LocalDateTime.of(2024, 7, j, 0, 0,0);

                transaction.setDate(localDateTime);
                transaction.setAmount(BigDecimal.valueOf(Math.random() * 100));
                transaction.setType(Math.random() < 0.5 ? TypeTransaction.SPENDING : TypeTransaction.EARNING);
                transaction.setUser(userRepository.findByEmail("aaa1@gmail.com").orElse(null));
                transaction.setCreatedAt(LocalDateTime.now());
                transaction.setCategory(categoryRepository.findByName("category#1").orElse(null));
                transaction.setAccount(accountRepository.findByName("account#1").orElse(null));
                transactionRepository.save(transaction);
            }
            // Move to the previous day
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }
}
