package com.github.jbarus.gradmasterbackend.pipelines;

import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroup;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterGroupType;
import com.github.jbarus.gradmasterbackend.pipelines.filters.FilterOrder;
import com.github.jbarus.gradmasterbackend.pipelines.filters.XLSXFilter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UniversityEmployeeExtractionPipeline {

    List<XLSXFilter> filters;

    @Autowired
    public UniversityEmployeeExtractionPipeline(List<XLSXFilter> filters) {
        this.filters = filters.stream()
                .filter(filter -> filter.getClass().isAnnotationPresent(FilterGroup.class))
                .filter(filter -> Arrays.stream(filter.getClass().getAnnotation(FilterGroup.class).value())
                        .anyMatch(order -> order.group() == FilterGroupType.UNIVERSITY_EMPLOYEE))
                .sorted(Comparator.comparingInt(filter ->
                        Arrays.stream(filter.getClass().getAnnotation(FilterGroup.class).value())
                                .filter(order -> order.group() == FilterGroupType.UNIVERSITY_EMPLOYEE)
                                .findFirst()
                                .map(FilterOrder::order)
                                .orElse(Integer.MAX_VALUE)
                ))
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
