package com.github.jbarus.gradmasterbackend.pipelines.filters.universityEmployeeFilters;

import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.pipelines.filters.XLSXFilter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
@FilterOrder(2)
@FilterGroup(FilterGroupType.UNIVERSITY_EMPLOYEE)
public class FirstRowRemoverFilter implements XLSXFilter {
    @Override
    public void doFilter(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet.getPhysicalNumberOfRows() > 0) {
            XSSFRow rowToRemove = sheet.getRow(0);
            if (rowToRemove != null) {
                sheet.removeRow(rowToRemove);
            }

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    sheet.shiftRows(i, i, -1);
                }
            }
        }
    }
}
