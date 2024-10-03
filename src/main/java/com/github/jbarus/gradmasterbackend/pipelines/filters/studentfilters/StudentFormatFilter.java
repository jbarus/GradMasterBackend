package com.github.jbarus.gradmasterbackend.pipelines.filters.studentfilters;

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
        @FilterOrder(group = FilterGroupType.STUDENT, order = 0)
})
public class StudentFormatFilter implements XLSXFilter {
    Set<String> columnsToKeep = new HashSet<>(List.of(
            "IMIE",
            "NAZWISKO",
            "RECENZENT",
            "DATA_OBRONY"
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
