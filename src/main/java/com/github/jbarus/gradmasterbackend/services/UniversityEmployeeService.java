package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.pipelines.UniversityEmployeeExtractionPipeline;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
public class UniversityEmployeeService {
    private final UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline;

    public UniversityEmployeeService(UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline) {
        this.universityEmployeeExtractionPipeline = universityEmployeeExtractionPipeline;
    }

    public HashMap<LocalDate, List<UniversityEmployee>> prepareUniversityEmployees(MultipartFile file) throws Exception {
        XSSFWorkbook workbook;
        try{
            workbook = new XSSFWorkbook(file.getInputStream());
        }catch (Exception e){
            return null;
        }

        universityEmployeeExtractionPipeline.doFilter(workbook);

        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);
        if(workbookData.isEmpty()){
            return null;
        }

        HashMap<LocalDate, List<List<String>>> employeesByDate = XLSXUtils.splitByDates(workbookData, 3);

        HashMap<LocalDate, List<UniversityEmployee>> universityEmployeesByDate = new HashMap<>();
        for(Map.Entry<LocalDate, List<List<String>>> entry : employeesByDate.entrySet()){
            Context context = Context.getInstance(entry.getKey());
            List<UniversityEmployee> universityEmployees = XLSXUtils.convertRawDataToUniversityEmployee(entry.getValue());
            context.setUniversityEmployeeList(universityEmployees);
            universityEmployeesByDate.put(entry.getKey(), universityEmployees);
        }

        return universityEmployeesByDate;
    }
}
