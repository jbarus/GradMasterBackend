package com.github.jbarus.gradmasterbackend.pipelines.filters.universityemployeefilters;

import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.pipelines.filters.XLSXFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@FilterGroup({
        @FilterOrder(group = FilterGroupType.UNIVERSITY_EMPLOYEE, order = 1)
})
public class UniversityEmployeeColumnsExtractorFilter implements XLSXFilter {

    Set<String> columnsToKeep = new HashSet<>(List.of(
            "NAZWISKO",
            "IMIE",
            "CZY_HABILITOWANY",
            "POCZATEK_DOSTEPNOSCI",
            "KONIEC_DOSTEPNOSCI",
            "DLUGOSC_KOMISJI"
    ));

    @Override
    public void doFilter(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);
        Set<String> columnsToKeepTemp = new HashSet<>(columnsToKeep);
        for (int index = 0; index < sheet.getRow(0).getLastCellNum(); index++) {
            Cell cell = sheet.getRow(0).getCell(index);
            if (cell != null && !columnsToKeepTemp.contains(cell.getStringCellValue())) {
                removeColumn(sheet,index);
            } else {
                columnsToKeepTemp.remove(cell.getStringCellValue());
            }
        }
    }

    private void removeColumn(XSSFSheet sheet, int columnIndex) {
        for (int rowIndex = 0; rowIndex < sheet.getLastRowNum()+1; rowIndex++) {
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
