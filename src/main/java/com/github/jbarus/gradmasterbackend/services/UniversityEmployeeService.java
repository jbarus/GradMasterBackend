package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.pipelines.UniversityEmployeeExtractionPipeline;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityEmployeeService {
    private final UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline;

    public UniversityEmployeeService(UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline) {
        this.universityEmployeeExtractionPipeline = universityEmployeeExtractionPipeline;
    }

    public List<UniversityEmployee> prepareUniversityEmployees(MultipartFile file) throws Exception {
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

        List<UniversityEmployee> universityEmployees;
        universityEmployees = XLSXUtils.convertRawDataToUniversityEmployee(workbookData);

        return universityEmployees;
    }
}
