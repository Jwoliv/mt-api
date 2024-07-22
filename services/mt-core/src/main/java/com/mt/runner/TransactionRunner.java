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

    @Override
    public void run(String... args) throws Exception {
        this.createAndSaveTransactions();
    }

    private void createAndSaveTransactions() {
        Calendar calendar = Calendar.getInstance();
        boolean isExample = true;
        for (int i = 0; i < 20; i++) {
            for (int j = 1; j < 24; j++) {
                Transaction transaction = new Transaction();

                // Set date to a random time on the current day

                // Create a random time within the day for createdAt

                LocalDateTime localDateTime = LocalDateTime.of(2024, 7, j, 0, 0,0);

                transaction.setDate(localDateTime);
                transaction.setAmount(BigDecimal.valueOf(Math.random() * 100));
                transaction.setType(isExample ? TypeTransaction.SPENDING : TypeTransaction.EARNING);
                transaction.setUser(userRepository.findByEmail("aaa1@gmail.com").orElse(null));
                transaction.setCreatedAt(LocalDateTime.now());
                isExample = !isExample;
                transactionRepository.save(transaction);
            }
            // Move to the previous day
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }

    private LocalDateTime generateRandomLocalDateTimeWithinDay(Calendar calendar) {
        LocalDateTime startOfDay = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        long startEpochSecond = startOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpochSecond = endOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();
        long randomEpochSecond = startEpochSecond + (long) (Math.random() * (endEpochSecond - startEpochSecond));

        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneId.systemDefault().getRules().getOffset(startOfDay));
    }
}
