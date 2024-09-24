package com.github.jbarus.gradmasterbackend.Pipelines.Filters.UniversityEmployeeFilters;

import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.XLSXFilter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@FilterOrder(0)
@FilterGroup(FilterGroupType.UNIVERSITY_EMPLOYEE)
public class FormatFilter implements XLSXFilter {
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

        Set<String> fileColumnNames = new HashSet<>();
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            if(sheet.getRow(0).getCell(i) != null) {
                fileColumnNames.add(sheet.getRow(0).getCell(i).getStringCellValue());
            }
        }

        if(!fileColumnNames.containsAll(columnsToKeep)) {
            throw new Exception("File does not contain all necessary columns");
        }
    }
}
