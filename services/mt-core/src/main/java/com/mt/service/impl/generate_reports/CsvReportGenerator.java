package com.mt.service.impl.generate_reports;

import com.mt.repository.AccountRepository;
import com.mt.repository.DailyAmountReportRepository;
import com.mt.repository.TransactionRepository;
import com.mt.utils.FileFormatter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;

import static com.mt.utils.FileFormatter.CSV;
import static com.mt.utils.FileFormatter.CSV_CONTENT_TYPE;

@Service("csvReportGenerator")
public class CsvReportGenerator implements ReportGenerator {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private AccountRepository accountRepository;
    @Setter(onMethod = @__({@Autowired}))
    private DailyAmountReportRepository dailyAmountReportRepository;

    private final String[] TRANSACTION_CSV_HEADER = {"ID", "Date", "Amount", "Type", "Category", "Account", "ReceiverAccount", "Sender", "Note", "CreatedAt", "UpdatedAt"};
    private final String[] TRANSACTION_NAME_MAPPING = {"id", "date", "amount", "type", "category", "account", "receiverAccount", "sender", "note", "createdAt", "updatedAt"};

    private final String[] ACCOUNT_CSV_HEADER = {"ID", "Name", "SpendMoney", "EarnMoney", "CurrentBalance", "CreatedAt", "UpdatedAt"};
    private final String[] ACCOUNT_NAME_MAPPING = {"id", "name", "spendMoney", "earnMoney", "currentBalance", "createdAt", "updatedAt"};

    private final String[] DAILY_AMOUNT_CSV_HEADER = {"ID", "Amount", "Date"};
    private final String[] DAILY_AMOUNT_NAME_MAPPING = {"id", "amount", "date"};

    private final String[] DAILY_REPORTS_CSV_HEADER = {"Spending", "Earning", "Date"};
    private final String[] DAILY_REPORTS_NAME_MAPPING = {"spending", "earning", "date"};

    @Override
    public void getAllReports(String email, HttpServletResponse response) {
        try {
            FileFormatter.updateHttpResponse(response, CSV_CONTENT_TYPE, CSV);
            var csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            writeAllReports(email, csvWriter);
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate combined CSV report", e);
        }
    }

    private void writeAllReports(String email, CsvBeanWriter csvWriter) throws IOException {
        writeTransactionReport(csvWriter, email);
        writeAccountReport(csvWriter, email);
        writeDailyAmountReport(csvWriter, email);
        writeDailyReport(csvWriter, email);
    }

    private void writeTransactionReport(ICsvBeanWriter csvWriter, String email) throws IOException {
        csvWriter.writeHeader(TRANSACTION_CSV_HEADER);
        transactionRepository.getUserTransactionsByEmail(email).forEach(transaction -> {
            try {
                csvWriter.write(transaction, TRANSACTION_NAME_MAPPING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write transaction report", e);
            }
        });
    }

    private void writeAccountReport(ICsvBeanWriter csvWriter, String email) throws IOException {
        csvWriter.writeHeader(ACCOUNT_CSV_HEADER);
        accountRepository.findAccountsByEmail(email).forEach(account -> {
            try {
                csvWriter.write(account, ACCOUNT_NAME_MAPPING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write account report", e);
            }
        });
    }

    private void writeDailyAmountReport(ICsvBeanWriter csvWriter, String email) throws IOException {
        csvWriter.writeHeader(DAILY_AMOUNT_CSV_HEADER);
        dailyAmountReportRepository.findAllByEmail(email).forEach(report -> {
            try {
                csvWriter.write(report, DAILY_AMOUNT_NAME_MAPPING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write daily amount report", e);
            }
        });
    }

    private void writeDailyReport(ICsvBeanWriter csvWriter, String email) throws IOException {
        csvWriter.writeHeader(DAILY_REPORTS_CSV_HEADER);
        transactionRepository.getAllDailyUserReport(email).forEach(report -> {
            try {
                csvWriter.write(report, DAILY_REPORTS_NAME_MAPPING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write daily report", e);
            }
        });
    }
}
