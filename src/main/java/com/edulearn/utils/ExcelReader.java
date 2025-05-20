package com.edulearn.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading Excel files
 */
public class ExcelReader {
    
    private static final Logger logger = LogManager.getLogger(ExcelReader.class);
    
    private Workbook workbook;
    private String filePath;
    
    /**
     * Constructor
     *
     * @param filePath path to Excel file
     */
    public ExcelReader(String filePath) {
        this.filePath = filePath;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(fileInputStream);
            logger.info("Excel file loaded: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to load Excel file: {}", filePath, e);
            throw new RuntimeException("Failed to load Excel file: " + filePath, e);
        }
    }
    
    /**
     * Gets sheet data as list of maps
     *
     * @param sheetName name of the sheet
     * @return list of maps where each map represents a row with column headers as keys
     */
    public List<Map<String, String>> getSheetData(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                logger.error("Sheet not found: {}", sheetName);
                return data;
            }
            
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                logger.error("Header row not found in sheet: {}", sheetName);
                return data;
            }
            
            int lastRowNum = sheet.getLastRowNum();
            int lastCellNum = headerRow.getLastCellNum();
            
            // Get header values
            String[] headers = new String[lastCellNum];
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                headers[i] = cell != null ? getCellValueAsString(cell) : "";
            }
            
            // Get data rows
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j);
                    String value = cell != null ? getCellValueAsString(cell) : "";
                    rowData.put(headers[j], value);
                }
                
                data.add(rowData);
            }
            
            logger.info("Read {} rows from sheet: {}", data.size(), sheetName);
            
        } catch (Exception e) {
            logger.error("Error reading sheet: {}", sheetName, e);
        }
        
        return data;
    }
    
    /**
     * Gets data from sheet in 2D array format for TestNG DataProvider
     *
     * @param sheetName name of the sheet
     * @return 2D Object array with sheet data
     */
    public Object[][] getDataAsObjectArray(String sheetName) {
        List<Map<String, String>> sheetData = getSheetData(sheetName);
        
        Object[][] data = new Object[sheetData.size()][1];
        for (int i = 0; i < sheetData.size(); i++) {
            data[i][0] = sheetData.get(i);
        }
        
        return data;
    }
    
    /**
     * Gets cell value as string
     *
     * @param cell Excel cell
     * @return cell value as string
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        String value;
        
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue().toString();
                } else {
                    // Convert numeric to string without .0 for whole numbers
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        value = String.valueOf((long) numericValue);
                    } else {
                        value = String.valueOf(numericValue);
                    }
                }
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                value = "";
        }
        
        return value;
    }
    
    /**
     * Closes the workbook
     */
    public void close() {
        if (workbook != null) {
            try {
                workbook.close();
                logger.info("Excel workbook closed: {}", filePath);
            } catch (IOException e) {
                logger.error("Error closing workbook: {}", filePath, e);
            }
        }
    }
}
