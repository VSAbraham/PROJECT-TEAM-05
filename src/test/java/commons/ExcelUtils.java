package commons;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class ExcelUtils {

    public static List<Map<String, String>> readExcel(String filePath) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                System.out.println("[ERROR] Header row is missing.");
                return dataList;
            }

            int totalColumns = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> dataMap = new LinkedHashMap<>();

                for (int j = 0; j < totalColumns; j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell valueCell = row.getCell(j);

                    String key = (headerCell != null) ? headerCell.toString().trim() : "Column" + j;
                    String value = (valueCell != null) ? valueCell.toString().trim() : "";

                    dataMap.put(key, value);
                }

                // Optional: skip completely empty rows
                boolean isEmptyRow = dataMap.values().stream().allMatch(String::isEmpty);
                if (!isEmptyRow) {
                    dataList.add(dataMap);
                } else {
                    System.out.println("[INFO] Skipping empty row at index: " + i);
                }
            }

        } catch (Exception e) {
            System.out.println("[ERROR] Failed to read Excel file: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList;
    }
}