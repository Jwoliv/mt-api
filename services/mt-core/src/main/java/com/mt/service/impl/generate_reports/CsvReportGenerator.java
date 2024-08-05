package com.mt.service.impl.generate_reports;

import com.mt.repository.AccountRepository;
import com.mt.repository.DailyAmountReportRepository;
import com.mt.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@Service("csvReportGenerator")
public class CsvReportGenerator implements ReportGenerator {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private AccountRepository accountRepository;
    @Setter(onMethod = @__({@Autowired}))
    private DailyAmountReportRepository dailyAmountReportRepository;

    private final List<String> TRANSACTIONS_HEADER_TITLES = List.of(
            "id", "date", "amount", "type", "category", "account", "receiverAccount", "sender", "note", "createdAt", "updatedAt"
    );
    private final List<String> ACCOUNTS_HEADER_TITLES = List.of(
            "id", "name", "spendMoney", "earnMoney", "currentBalance", "createdAt", "updatedAt"
    );
    private final List<String> DAILY_AMOUNT_HEADER_TITLES = List.of("id", "amount", "date");
    private final List<String> DAILY_REPORTS_HEADER_TITLES = List.of("spending", "earning", "date");

    @Override
    public void getAllReports(String email, HttpServletResponse response) {
        try (var writer = new StringWriter()) {
            writeTransactionReport(writer, email);
            writeAccountReport(writer, email);
            writeDailyReport(writer, email);
            writeDailyAmountReport(writer, email);

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=report.csv");
            var ops = response.getOutputStream();
            writer.write(ops.toString());
            ops.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate combined CSV report", e);
        }
    }

    private void writeTransactionReport(StringWriter writer, String email) {
        writer.write(String.join(",", TRANSACTIONS_HEADER_TITLES));
        writer.write("\n");
        transactionRepository.getUserTransactionsByEmail(email).forEach(transaction -> {
            writer.write(toCsvField(transaction.getId()));
            writer.write(",");
            writer.write(toCsvField(transaction.getDate()));
            writer.write(",");
            writer.write(toCsvField(transaction.getAmount()));
            writer.write(",");
            writer.write(toCsvField(transaction.getType()));
            writer.write(",");
            writer.write(toCsvField(transaction.getCategory()));
            writer.write(",");
            writer.write(toCsvField(transaction.getAccount()));
            writer.write(",");
            writer.write(toCsvField(transaction.getReceiverAccount()));
            writer.write(",");
            writer.write(toCsvField(transaction.getSender()));
            writer.write(",");
            writer.write(toCsvField(transaction.getNote()));
            writer.write(",");
            writer.write(toCsvField(transaction.getCreatedAt()));
            writer.write(",");
            writer.write(toCsvField(transaction.getUpdatedAt()));
            writer.write("\n");
        });
    }

    private void writeAccountReport(StringWriter writer, String email) {
        writer.write(String.join(",", ACCOUNTS_HEADER_TITLES));
        writer.write("\n");
        accountRepository.findAccountsByEmail(email).forEach(account -> {
            writer.write(toCsvField(account.getId()));
            writer.write(",");
            writer.write(toCsvField(account.getName()));
            writer.write(",");
            writer.write(toCsvField(account.getSpendMoney()));
            writer.write(",");
            writer.write(toCsvField(account.getEarnMoney()));
            writer.write(",");
            writer.write(toCsvField(account.getCurrentBalance()));
            writer.write(",");
            writer.write(toCsvField(account.getCreatedAt()));
            writer.write(",");
            writer.write(toCsvField(account.getUpdatedAt()));
            writer.write("\n");
        });
    }

    private void writeDailyReport(StringWriter writer, String email) {
        writer.write(String.join(",", DAILY_REPORTS_HEADER_TITLES));
        writer.write("\n");
        transactionRepository.getAllDailyUserReport(email).forEach(report -> {
            writer.write(toCsvField(report.getEarning()));
            writer.write(",");
            writer.write(toCsvField(report.getSpending()));
            writer.write(",");
            writer.write(toCsvField(report.getDate()));
            writer.write("\n");
        });
    }

    private void writeDailyAmountReport(StringWriter writer, String email) {
        writer.write(String.join(",", DAILY_AMOUNT_HEADER_TITLES));
        writer.write("\n");
        dailyAmountReportRepository.findAllByEmail(email).forEach(report -> {
            writer.write(toCsvField(report.getId()));
            writer.write(",");
            writer.write(toCsvField(report.getAmount()));
            writer.write(",");
            writer.write(toCsvField(report.getDate()));
            writer.write("\n");
        });
    }

    private String toCsvField(Object value) {
        return Optional.ofNullable(value).map(Object::toString).orElse("-");
    }
}
