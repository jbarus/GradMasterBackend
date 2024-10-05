package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.exceptions.MissingColumnsException;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResponse;
import com.github.jbarus.gradmasterbackend.pipelines.UniversityEmployeeExtractionPipeline;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<UploadResponse> prepareUniversityEmployees(MultipartFile file) {
        XSSFWorkbook workbook;
        try{
            workbook = new XSSFWorkbook(file.getInputStream());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(UploadResponse.INVALID_INPUT);
        }

        try{
            universityEmployeeExtractionPipeline.doFilter(workbook);
        }catch (MissingColumnsException ex){
            return ResponseEntity.badRequest().body(UploadResponse.INVALID_CONTENT);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(UploadResponse.PARSING_ERROR);
        }


        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);
        if(workbookData.isEmpty()){
            return ResponseEntity.badRequest().body(UploadResponse.PARSING_ERROR);
        }

        HashMap<LocalDate, List<List<String>>> employeesByDate = XLSXUtils.splitByDates(workbookData, 3);

        HashMap<LocalDate, List<UniversityEmployee>> universityEmployeesByDate = new HashMap<>();
        for(Map.Entry<LocalDate, List<List<String>>> entry : employeesByDate.entrySet()){
            Context context = Context.getInstance(entry.getKey());
            List<UniversityEmployee> universityEmployees = XLSXUtils.convertRawDataToUniversityEmployee(entry.getValue());
            context.setUniversityEmployeeList(universityEmployees);
            universityEmployeesByDate.put(entry.getKey(), universityEmployees);
        }

        return ResponseEntity.ok().body(UploadResponse.SUCCESS);
    }
}
