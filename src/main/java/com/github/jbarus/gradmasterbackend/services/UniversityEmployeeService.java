package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.MissingColumnsException;
import com.github.jbarus.gradmasterbackend.exceptions.MultipleDatesException;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.Response;
import com.github.jbarus.gradmasterbackend.models.communication.UploadStatus;
import com.github.jbarus.gradmasterbackend.models.dto.UniversityEmployeeDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.pipelines.UniversityEmployeeExtractionPipeline;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UniversityEmployeeService {
    private final UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline;

    public UniversityEmployeeService(UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline) {
        this.universityEmployeeExtractionPipeline = universityEmployeeExtractionPipeline;
    }

    public ResponseEntity<Response<UploadStatus, UniversityEmployeeDTO>> handleUniversityEmployeeFile(MultipartFile file, UUID id){
        XSSFWorkbook workbook;
        try{
            workbook = new XSSFWorkbook(file.getInputStream());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_INPUT, null));
        }

        try{
            universityEmployeeExtractionPipeline.doFilter(workbook);
        }catch (MissingColumnsException ex){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_CONTENT, null));
        }catch (MultipleDatesException ex){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.MULTIPLE_DATES, null));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.PARSING_ERROR, null));
        }

        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);

        if(workbookData.isEmpty()){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.PARSING_ERROR));
        }

        List<UniversityEmployee> universityEmployees;
        ProblemContext problemContext = ProblemContext.getInstance(id);

        if(problemContext == null){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.UNINITIALIZED_CONTEXT, null));
        }

        universityEmployees = XLSXUtils.convertRawDataToUniversityEmployee(workbookData);
        problemContext.setUniversityEmployees(universityEmployees);

        return ResponseEntity.ok().body(new Response<>(UploadStatus.SUCCESS,new UniversityEmployeeDTO(problemContext.getUuid(),universityEmployees)));
    }

    public ResponseEntity<List<UniversityEmployee>> getUniversityEmployeeByContext(UUID id) {
        ProblemContext problemContext = ProblemContext.getInstance(id);
        if(problemContext == null || problemContext.getUniversityEmployees() == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(problemContext.getUniversityEmployees());
    }

    public ResponseEntity<List<UniversityEmployee>> updateUniversityEmployeeByContext(UUID id, List<UniversityEmployee> universityEmployees) {
        ProblemContext problemContext = ProblemContext.getInstance(id);
        if(problemContext == null || problemContext.getUniversityEmployees() == null){
            return ResponseEntity.badRequest().build();
        }

        problemContext.setUniversityEmployees(universityEmployees);

        return ResponseEntity.ok().body(problemContext.getUniversityEmployees());
    }
}
