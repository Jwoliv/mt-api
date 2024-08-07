package com.mt.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileFormatter {

    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CSV_CONTENT_TYPE = "text/csv";
    public static final String XLSX = "xlsx";
    public static final String CSV = "csv";


    private static final String CONTENT_DISPOSITION_TEMPLATE = "attachment; filename=report_%s.%s";

    public static void updateHttpResponse(HttpServletResponse response, String contentType, String extensionFile) {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", generateContentDisposition(extensionFile));
    }

    private static String generateContentDisposition(String extensionFile) {
        return CONTENT_DISPOSITION_TEMPLATE.formatted(FileFormatter.formatCurrentDate(), extensionFile);
    }

    private static String formatCurrentDate() {
        var formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        return formatter.format(new Date());
    }
}
