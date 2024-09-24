package com.github.jbarus.gradmasterbackend.Pipelines.Filters.UniversityEmployeeFilters;

import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.XLSXFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@FilterOrder(1)
@FilterGroup(FilterGroupType.UNIVERSITY_EMPLOYEE)
public class ColumnsExtractorFilter implements XLSXFilter {

    Set<String> columnsToKeep = new HashSet<String>(List.of(
            "NAZWISKO",
            "IMIE",
            "CZY_HABILITOWANY",
            "DATA_DOSTEPNOSCI",
            "POCZATEK_DOSTEPNOSCI",
            "KONIEC_DOSTEPNOSCI",
            "DLUGOSC_KOMISJI"
    ));

    @Override
    public void doFilter(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int index = 0; index < sheet.getRow(0).getLastCellNum(); index++) {
            Cell cell = sheet.getRow(0).getCell(index);
            if (cell != null && !columnsToKeep.contains(cell.getStringCellValue())) {
                removeColumn(sheet,index);
            }
        }
    }

    private void removeColumn(XSSFSheet sheet, int columnIndex) {
        for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    row.removeCell(cell);
                }
            }
        }
    }
}
