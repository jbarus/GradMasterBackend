package com.github.jbarus.gradmasterbackend.pipelines.filters;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface XLSXFilter {
    void doFilter(XSSFWorkbook workbook) throws Exception;
}
