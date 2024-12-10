package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.*;
import com.github.jbarus.gradmasterbackend.mappers.UniversityEmployeeMapper;
import com.github.jbarus.gradmasterbackend.models.Student;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UniversityEmployeeService {
    private final UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline;

    public UniversityEmployeeService(UniversityEmployeeExtractionPipeline universityEmployeeExtractionPipeline) {
        this.universityEmployeeExtractionPipeline = universityEmployeeExtractionPipeline;
    }

    public UniversityEmployeeDTO handleUniversityEmployeeFile(MultipartFile file, UUID id) {
        XSSFWorkbook workbook;

        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid file");
        }

        try {
            universityEmployeeExtractionPipeline.doFilter(workbook);
        } catch (MissingColumnsException e) {
            throw new BusinessLogicException(UploadStatus.INVALID_CONTENT);
        } catch (MultipleDatesException e) {
            throw new BusinessLogicException(UploadStatus.MULTIPLE_DATES);
        } catch (Exception e) {
            throw new BusinessLogicException(UploadStatus.PARSING_ERROR);
        }

        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);

        if (workbookData.isEmpty()) {
            throw new BusinessLogicException(UploadStatus.PARSING_ERROR);
        }

        ProblemContext problemContext = ProblemContext.getInstance(id);

        if (problemContext == null) {
            throw new UninitializedContextException("No such context");
        }

        List<UniversityEmployee> universityEmployees = XLSXUtils.convertRawDataToUniversityEmployee(workbookData);
        problemContext.setUniversityEmployees(universityEmployees);
        problemContext.setStudents(null);
        problemContext.setStudentReviewerMapping(null);

        return UniversityEmployeeMapper.convertUniversityEmployeeListToUniversityEmployeeDTO(problemContext);
    }

    public UniversityEmployeeDTO getUniversityEmployeeByContext(UUID id) {
        ProblemContext problemContext = ProblemContext.getInstance(id);

        if (problemContext == null || problemContext.getUniversityEmployees() == null) {
            throw new UninitializedContextException("No such context");
        }

        return UniversityEmployeeMapper.convertUniversityEmployeeListToUniversityEmployeeDTO(problemContext);
    }

    public UniversityEmployeeDTO updateUniversityEmployeeByContext(UUID id, List<UniversityEmployee> updatedEmployees) {
        ProblemContext problemContext = ProblemContext.getInstance(id);

        if (problemContext == null || problemContext.getUniversityEmployees() == null) {
            throw new UninitializedContextException("No such context");
        }
        for (UniversityEmployee universityEmployee : updatedEmployees) {
            if(!problemContext.getUniversityEmployees().stream().map(UniversityEmployee::getId).toList().contains(universityEmployee.getId())) {
                throw new BusinessLogicException(UploadStatus.UNAUTHORIZEDMODIFICATION);
            }
        }
        problemContext.setUniversityEmployees(updatedEmployees);
        return UniversityEmployeeMapper.convertUniversityEmployeeListToUniversityEmployeeDTO(problemContext);
    }
}
