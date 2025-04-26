package com.playwright.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    private static Workbook workbook;
    private static Sheet sheet;

    public static void loadExcel(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheet(sheetName);
    }

    public static List<Map<String, String>> getTestData() {
        List<Map<String, String>> testData = new ArrayList<>();
        
        // Get header row
        Row headerRow = sheet.getRow(0);
        int lastRowNum = sheet.getLastRowNum();
        
        // Iterate through data rows
        for (int i = 1; i <= lastRowNum; i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null) continue;
            
            Map<String, String> rowData = new HashMap<>();
            
            // Iterate through columns
            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                String header = getCellValueAsString(headerRow.getCell(j));
                String value = getCellValueAsString(currentRow.getCell(j));
                rowData.put(header, value);
            }
            
            testData.add(rowData);
        }
        
        return testData;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public static void closeExcel() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
} 