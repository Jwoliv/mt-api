package com.mt.service.impl.generate_reports;

import com.mt.repository.AccountRepository;
import com.mt.repository.DailyAmountReportRepository;
import com.mt.repository.TransactionRepository;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
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
    public ByteArrayInputStream getTransactionReports(String email) {
        return generateReport(
                "transactions",
                TRANSACTIONS_HEADER_TITLES,
                transactionRepository.getUserTransactionsByEmail(email),
                (row, transaction) -> {
                    row.createCell(0).setCellValue(transaction.getId().toString());
                    row.createCell(1).setCellValue(transaction.getDate().toString());
                    row.createCell(2).setCellValue(transaction.getAmount().toString());
                    row.createCell(3).setCellValue(transaction.getType().name());
                    row.createCell(4).setCellValue(transaction.getCategory().getId().toString());
                    row.createCell(5).setCellValue(transaction.getAccount().getId().toString());
                    row.createCell(6).setCellValue(transaction.getReceiverAccount().getId().toString());
                    row.createCell(7).setCellValue(transaction.getSender());
                    row.createCell(8).setCellValue(transaction.getNote());
                    row.createCell(9).setCellValue(transaction.getCreatedAt().toString());
                    row.createCell(10).setCellValue(transaction.getUpdatedAt().toString());
                }
        );
    }

    @Override
    public ByteArrayInputStream getAccountReports(String email) {
        return generateReport(
                "accounts",
                ACCOUNTS_HEADER_TITLES,
                accountRepository.findAccountsByEmail(email),
                (row, account) -> {
                    row.createCell(0).setCellValue(account.getId().toString());
                    row.createCell(1).setCellValue(account.getName());
                    row.createCell(2).setCellValue(account.getSpendMoney().toString());
                    row.createCell(3).setCellValue(account.getEarnMoney().toString());
                    row.createCell(4).setCellValue(account.getCurrentBalance().toString());
                    row.createCell(5).setCellValue(account.getCreatedAt().toString());
                    row.createCell(6).setCellValue(account.getUpdatedAt().toString());
                }
        );
    }

    @Override
    public ByteArrayInputStream getDailyReports(String email) {
        return generateReport(
                "daily_spending_earning_reports",
                DAILY_REPORTS_HEADER_TITLES,
                transactionRepository.getAllDailyUserReport(email),
                (row, report) -> {
                    row.createCell(0).setCellValue(report.getEarning().toString());
                    row.createCell(1).setCellValue(report.getSpending().toString());
                    row.createCell(2).setCellValue(report.getDate().toString());
                }
        );
    }

    @Override
    public ByteArrayInputStream getDailyAmountReports(String email) {
        return generateReport(
                "daily_amount_reports",
                DAILY_AMOUNT_HEADER_TITLES,
                dailyAmountReportRepository.findAllByEmail(email),
                (row, report) -> {
                    row.createCell(0).setCellValue(report.getId().toString());
                    row.createCell(1).setCellValue(report.getAmount().toString());
                    row.createCell(2).setCellValue(report.getDate().toString());
                }
        );
    }

    public ByteArrayInputStream getAllReports(String email) {
        try (var workbook = new XSSFWorkbook(); var outputStream = new ByteArrayOutputStream()) {
            addReportSheet(workbook, "transactions", TRANSACTIONS_HEADER_TITLES, transactionRepository.getUserTransactionsByEmail(email),
                    (row, transaction) -> {
                        row.createCell(0).setCellValue(transaction.getId().toString());
                        row.createCell(1).setCellValue(transaction.getDate().toString());
                        row.createCell(2).setCellValue(transaction.getAmount().toString());
                        row.createCell(3).setCellValue(transaction.getType().name());
                        row.createCell(4).setCellValue(transaction.getCategory().getId().toString());
                        row.createCell(5).setCellValue(transaction.getAccount().getId().toString());
                        row.createCell(6).setCellValue(transaction.getReceiverAccount().getId().toString());
                        row.createCell(7).setCellValue(transaction.getSender());
                        row.createCell(8).setCellValue(transaction.getNote());
                        row.createCell(9).setCellValue(transaction.getCreatedAt().toString());
                        row.createCell(10).setCellValue(transaction.getUpdatedAt().toString());
                    }
            );

            addReportSheet(workbook, "accounts", ACCOUNTS_HEADER_TITLES, accountRepository.findAccountsByEmail(email),
                    (row, account) -> {
                        row.createCell(0).setCellValue(account.getId().toString());
                        row.createCell(1).setCellValue(account.getName());
                        row.createCell(2).setCellValue(account.getSpendMoney().toString());
                        row.createCell(3).setCellValue(account.getEarnMoney().toString());
                        row.createCell(4).setCellValue(account.getCurrentBalance().toString());
                        row.createCell(5).setCellValue(account.getCreatedAt().toString());
                        row.createCell(6).setCellValue(account.getUpdatedAt().toString());
                    }
            );

            addReportSheet(workbook, "daily_spending_earning_reports", DAILY_REPORTS_HEADER_TITLES, transactionRepository.getAllDailyUserReport(email),
                    (row, report) -> {
                        row.createCell(0).setCellValue(report.getEarning().toString());
                        row.createCell(1).setCellValue(report.getSpending().toString());
                        row.createCell(2).setCellValue(report.getDate().toString());
                    }
            );

            addReportSheet(workbook, "daily_amount_reports", DAILY_AMOUNT_HEADER_TITLES, dailyAmountReportRepository.findAllByEmail(email),
                    (row, report) -> {
                        row.createCell(0).setCellValue(report.getId().toString());
                        row.createCell(1).setCellValue(report.getAmount().toString());
                        row.createCell(2).setCellValue(report.getDate().toString());
                    }
            );

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate combined Excel report", e);
        }
    }

    private <T> void addReportSheet(XSSFWorkbook workbook, String sheetName, List<String> headers, List<T> data, BiConsumer<XSSFRow, T> rowMapper) {
        var sheet = workbook.createSheet(sheetName);
        var headerRow = sheet.createRow(0);
        IntStream.range(0, headers.size()).forEach(i -> headerRow.createCell(i).setCellValue(headers.get(i)));

        int rowIndex = 1;
        for (T item : data) {
            var row = (XSSFRow) sheet.createRow(rowIndex++);
            rowMapper.accept(row, item);
        }
    }

    private <T> ByteArrayInputStream generateReport(String sheetName, List<String> headers, List<T> data, BiConsumer<XSSFRow, T> rowMapper) {
        try (var workbook = new XSSFWorkbook(); var outputStream = new ByteArrayOutputStream()) {
            addReportSheet(workbook, sheetName, headers, data, rowMapper);
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }
}
