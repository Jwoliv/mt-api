package com.mt.service.impl.generate_reports;

import com.mt.model.DailyAmountReport;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Transaction;
import com.mt.repository.AccountRepository;
import com.mt.repository.DailyAmountReportRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.view.DailyReportView;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service("excelReportGenerator")
public class ExcelReportGenerator implements ReportGenerator {

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
        try (var workbook = new XSSFWorkbook()) {
            writeTransactionReport(workbook, email);
            writeAccountReport(workbook, email);
            writeDailyReport(workbook, email);
            writeDailyAmountReport(workbook, email);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
            var ops = response.getOutputStream();
            workbook.write(ops);
            ops.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate combined Excel report", e);
        }
    }

    private void writeTransactionReport(XSSFWorkbook workbook, String email) {
        var sheet = workbook.createSheet("transactions");
        var headerRow = sheet.createRow(0);
        createHeader(headerRow, TRANSACTIONS_HEADER_TITLES);

        var transactions = transactionRepository.getUserTransactionsByEmail(email);
        int rowIndex = 1;
        for (var transaction : transactions) {
            var row = sheet.createRow(rowIndex++);
            populateTransactionRow(row, transaction);
        }
    }

    private void writeAccountReport(XSSFWorkbook workbook, String email) {
        var sheet = workbook.createSheet("accounts");
        var headerRow = sheet.createRow(0);
        createHeader(headerRow, ACCOUNTS_HEADER_TITLES);

        var accounts = accountRepository.findAccountsByEmail(email);
        int rowIndex = 1;
        for (var account : accounts) {
            var row = sheet.createRow(rowIndex++);
            populateAccountRow(row, account);
        }
    }

    private void writeDailyReport(XSSFWorkbook workbook, String email) {
        var sheet = workbook.createSheet("daily_spending_earning_reports");
        var headerRow = sheet.createRow(0);
        createHeader(headerRow, DAILY_REPORTS_HEADER_TITLES);

        var reports = transactionRepository.getAllDailyUserReport(email);
        int rowIndex = 1;
        for (var report : reports) {
            var row = sheet.createRow(rowIndex++);
            populateDailyReportRow(row, report);
        }
    }

    private void writeDailyAmountReport(XSSFWorkbook workbook, String email) {
        var sheet = workbook.createSheet("daily_amount_reports");
        var headerRow = sheet.createRow(0);
        createHeader(headerRow, DAILY_AMOUNT_HEADER_TITLES);

        var reports = dailyAmountReportRepository.findAllByEmail(email);
        int rowIndex = 1;
        for (var report : reports) {
            var row = sheet.createRow(rowIndex++);
            populateDailyAmountReportRow(row, report);
        }
    }

    private void createHeader(Row row, List<String> headers) {
        IntStream.range(0, headers.size()).forEach(i -> row.createCell(i).setCellValue(headers.get(i)));
    }

    private void populateTransactionRow(Row row, Transaction transaction) {
        row.createCell(0).setCellValue(toXlsxField(transaction.getId()));
        row.createCell(1).setCellValue(toXlsxField(transaction.getDate()));
        row.createCell(2).setCellValue(toXlsxField(transaction.getAmount()));
        row.createCell(3).setCellValue(toXlsxField(transaction.getType()));
        row.createCell(4).setCellValue(toXlsxField(transaction.getCategory()));
        row.createCell(5).setCellValue(toXlsxField(transaction.getAccount()));
        row.createCell(6).setCellValue(toXlsxField(transaction.getReceiverAccount()));
        row.createCell(7).setCellValue(toXlsxField(transaction.getSender()));
        row.createCell(8).setCellValue(toXlsxField(transaction.getNote()));
        row.createCell(9).setCellValue(toXlsxField(transaction.getCreatedAt()));
        row.createCell(10).setCellValue(toXlsxField(transaction.getUpdatedAt()));
    }

    private void populateAccountRow(Row row, Account account) {
        row.createCell(0).setCellValue(toXlsxField(account.getId()));
        row.createCell(1).setCellValue(toXlsxField(account.getName()));
        row.createCell(2).setCellValue(toXlsxField(account.getSpendMoney()));
        row.createCell(3).setCellValue(toXlsxField(account.getEarnMoney()));
        row.createCell(4).setCellValue(toXlsxField(account.getCurrentBalance()));
        row.createCell(5).setCellValue(toXlsxField(account.getCreatedAt()));
        row.createCell(6).setCellValue(toXlsxField(account.getUpdatedAt()));
    }

    private void populateDailyReportRow(Row row, DailyReportView report) {
        row.createCell(0).setCellValue(toXlsxField(report.getEarning()));
        row.createCell(1).setCellValue(toXlsxField(report.getSpending()));
        row.createCell(2).setCellValue(toXlsxField(report.getDate()));
    }

    private void populateDailyAmountReportRow(Row row, DailyAmountReport report) {
        row.createCell(0).setCellValue(toXlsxField(report.getId()));
        row.createCell(1).setCellValue(toXlsxField(report.getAmount()));
        row.createCell(2).setCellValue(toXlsxField(report.getDate()));
    }

    private String toXlsxField(Object value) {
        return Optional.ofNullable(value).map(Object::toString).orElse("-");
    }
}
