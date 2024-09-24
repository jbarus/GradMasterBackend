package com.github.jbarus.gradmasterbackend.Pipelines.Filters.UniversityEmployeeFilters;

import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.XLSXFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
@FilterOrder(2)
@FilterGroup(FilterGroupType.UNIVERSITY_EMPLOYEE)
public class BlankCellsFilter implements XLSXFilter {
    @Override
    public void doFilter(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int rowIndex = sheet.getLastRowNum(); rowIndex >= 0; rowIndex--) {
            XSSFRow row = sheet.getRow(rowIndex);
            if (row != null && containsEmptyCell(row)) {
                removeRow(sheet, rowIndex);
            }
        }
    }

    private boolean containsEmptyCell(Row row) {
        for (Cell cell : row) {
            if (cell == null || cell.getCellType() == CellType.BLANK) {
                return true;
            }
        }
        return false;
    }

    private static void removeRow(XSSFSheet sheet, int rowIndex) {
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            Row removingRow = sheet.getRow(rowIndex);
            if (removingRow != null) {
                sheet.removeRow(removingRow);
            }
        }
    }
}
