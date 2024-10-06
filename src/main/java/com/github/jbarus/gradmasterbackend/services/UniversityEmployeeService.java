package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.exceptions.MissingColumnsException;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResponse;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResult;
import com.github.jbarus.gradmasterbackend.pipelines.UniversityEmployeeExtractionPipeline;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
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

    public ResponseEntity<UploadResponse<List<UUID>>> prepareUniversityEmployees(MultipartFile file) {
        XSSFWorkbook workbook;
        try{
            workbook = new XSSFWorkbook(file.getInputStream());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.INVALID_INPUT));
        }

        try{
            universityEmployeeExtractionPipeline.doFilter(workbook);
        }catch (MissingColumnsException ex){
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.INVALID_CONTENT));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.PARSING_ERROR));
        }


        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);
        if(workbookData.isEmpty()){
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.PARSING_ERROR));
        }

        HashMap<LocalDate, List<List<String>>> employeesByDate = XLSXUtils.splitByDates(workbookData, 3);

        List<UUID> contextIdsList = new ArrayList<>();
        for(Map.Entry<LocalDate, List<List<String>>> entry : employeesByDate.entrySet()){
            Context context = Context.getInstance(entry.getKey());
            List<UniversityEmployee> universityEmployees = XLSXUtils.convertRawDataToUniversityEmployee(entry.getValue());
            context.setUniversityEmployeeList(universityEmployees);
            contextIdsList.add(context.getId());
        }

        return ResponseEntity.ok().body(new UploadResponse<>(UploadResult.SUCCESS, contextIdsList));
    }
}
