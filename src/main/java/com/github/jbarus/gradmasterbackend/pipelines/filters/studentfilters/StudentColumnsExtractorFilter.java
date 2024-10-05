package com.github.jbarus.gradmasterbackend.pipelines.filters.studentfilters;

import com.github.jbarus.gradmasterbackend.exceptions.MissingColumnsException;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.pipelines.filters.XLSXFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.XSLFShapeContainer;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@FilterGroup({
        @FilterOrder(group = FilterGroupType.STUDENT, order = 1)
})
public class StudentColumnsExtractorFilter implements XLSXFilter {
    Set<String> columnsToKeep = new HashSet<>(List.of(
            "IMIE",
            "NAZWISKO",
            "RECENZENT",
            "DATA_OBRONY"
    ));

    @Override
    public void doFilter(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int index = 0; index < sheet.getRow(0).getLastCellNum(); index++) {
            Cell cell = sheet.getRow(0).getCell(index);
            if (cell != null && !columnsToKeep.contains(cell.getStringCellValue())) {
                removeColumn(sheet,index);
            } else {
                columnsToKeep.remove(cell.getStringCellValue());
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
