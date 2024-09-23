package com.github.jbarus.gradmasterbackend.Pipelines.Filters;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface XLSXFilter {
    void doFilter(XSSFWorkbook workbook) throws Exception;
}
