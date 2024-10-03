package com.github.jbarus.gradmasterbackend.pipelines.filters.universityemployeefilters;

import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.pipelines.filters.XLSXFilter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@FilterGroup({
        @FilterOrder(group = FilterGroupType.UNIVERSITY_EMPLOYEE, order = 0)
})
public class UniversityEmployeeFormatFilter implements XLSXFilter {
    Set<String> columnsToKeep = new HashSet<>(List.of(
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
