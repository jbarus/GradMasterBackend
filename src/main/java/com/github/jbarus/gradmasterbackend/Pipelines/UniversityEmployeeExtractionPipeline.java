package com.github.jbarus.gradmasterbackend.Pipelines;

import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.Pipelines.Filters.XLSXFilter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UniversityEmployeeExtractionPipeline {

    List<XLSXFilter> filters;

    @Autowired
    public UniversityEmployeeExtractionPipeline(List<XLSXFilter> filters) {
        this.filters = filters.stream()
                .filter(filter -> filter.getClass().isAnnotationPresent(FilterGroup.class) && filter.getClass().isAnnotationPresent(FilterOrder.class))
                .filter(filter -> filter.getClass().getAnnotation(FilterGroup.class).value() == FilterGroupType.UNIVERSITY_EMPLOYEE)
                .sorted(Comparator.comparingInt(filter -> filter.getClass().getAnnotation(FilterOrder.class).value()))
                .collect(Collectors.toList());
    }

    public void doFilter(XSSFWorkbook workbook) throws Exception {
        for (XLSXFilter filter : filters) {
            filter.doFilter(workbook);
        }
    }

    public void addFilterBefore(Class<? extends XLSXFilter> filterClass, XLSXFilter newFilter) {
        int index = findFilterIndex(filterClass);
        filters.add(index, newFilter);
    }

    public void addFilterAfter(Class<? extends XLSXFilter> filterClass, XLSXFilter newFilter) {
        int index = findFilterIndex(filterClass);
        filters.add(index + 1, newFilter);
    }

    private int findFilterIndex(Class<? extends XLSXFilter> filterClass) {
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getClass().equals(filterClass)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Filter not found: " + filterClass.getName());
    }
}
