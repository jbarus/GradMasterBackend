package com.github.jbarus.gradmasterbackend.pipelines.filters.studentfilters;

import com.github.jbarus.gradmasterbackend.pipelines.filters.XLSXFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class StudentMajorFilter implements XLSXFilter{
    private String column = "KIERUNEK";
    private String major = "Informatyka Techniczna";
    @Override
    public void doFilter(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);

        int targetColumnIndex = 0;
        for (Cell cell : headerRow) {
            if(cell.getStringCellValue().equals(column)){
                targetColumnIndex = cell.getColumnIndex();
                break;
            }
        }

        List<Row> rowsToRemove = new ArrayList<>();

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }

            Cell cell = row.getCell(targetColumnIndex);
            if (cell == null || !cell.getStringCellValue().equals(major)) {
                rowsToRemove.add(row);
            }
        }

        for (Row row : rowsToRemove) {
            sheet.removeRow(row);
        }
    }
}
