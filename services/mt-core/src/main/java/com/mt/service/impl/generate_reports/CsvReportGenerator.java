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
import java.util.function.Consumer;

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
        writeReport(csvWriter, TRANSACTION_CSV_HEADER, TRANSACTION_NAME_MAPPING, writer ->
                transactionRepository.getUserTransactionsByEmail(email).forEach(transaction -> writeCsv(writer, transaction, TRANSACTION_NAME_MAPPING)));

        writeReport(csvWriter, ACCOUNT_CSV_HEADER, ACCOUNT_NAME_MAPPING, writer ->
                accountRepository.findAccountsByEmail(email).forEach(account -> writeCsv(writer, account, ACCOUNT_NAME_MAPPING)));

        writeReport(csvWriter, DAILY_AMOUNT_CSV_HEADER, DAILY_AMOUNT_NAME_MAPPING, writer ->
                dailyAmountReportRepository.findAllByEmail(email).forEach(report -> writeCsv(writer, report, DAILY_AMOUNT_NAME_MAPPING)));

        writeReport(csvWriter, DAILY_REPORTS_CSV_HEADER, DAILY_REPORTS_NAME_MAPPING, writer ->
                transactionRepository.getAllDailyUserReport(email).forEach(report -> writeCsv(writer, report, DAILY_REPORTS_NAME_MAPPING)));
    }

    private void writeReport(ICsvBeanWriter csvWriter, String[] header, String[] nameMapping, Consumer<ICsvBeanWriter> reportWriter) throws IOException {
        csvWriter.writeHeader(header);
        reportWriter.accept(csvWriter);
    }

    private void writeCsv(ICsvBeanWriter csvWriter, Object bean, String[] nameMapping) {
        try {
            csvWriter.write(bean, nameMapping);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV report", e);
        }
    }
}
