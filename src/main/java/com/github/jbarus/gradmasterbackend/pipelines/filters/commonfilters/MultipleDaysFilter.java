package com.github.jbarus.gradmasterbackend.pipelines.filters.commonfilters;

import com.github.jbarus.gradmasterbackend.exceptions.MultipleDatesException;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.pipelines.filters.XLSXFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@FilterGroup({
        @FilterOrder(group = FilterGroupType.UNIVERSITY_EMPLOYEE, order = 1),
        @FilterOrder(group = FilterGroupType.STUDENT, order = 1)
})
public class MultipleDaysFilter implements XLSXFilter {
    private Set<String> columnsName = new HashSet<>(Set.of("DATA_OBRONY", "DATA_DOSTEPNOSCI"));

    @Override
    public void doFilter(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            throw new Exception("Header row is missing in the sheet");
        }

        int columnIndex = findColumnIndexByName(headerRow);

        if (columnIndex == -1) {
            return;
        }

        Set<String> uniqueDates = new HashSet<>();
        DataFormatter formatter = new DataFormatter();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null && !formatter.formatCellValue(cell).isEmpty()) {
                    String dateValue = formatter.formatCellValue(cell);
                    uniqueDates.add(dateValue);
                }
            }
        }

        if (uniqueDates.size() > 1) {
            throw new MultipleDatesException("Multiple dates found");
        }
    }

    private int findColumnIndexByName(Row headerRow) {
        for (Cell cell : headerRow) {
            if (cell.getCellType() == CellType.STRING && columnsName.contains(cell.getStringCellValue())) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }
}
