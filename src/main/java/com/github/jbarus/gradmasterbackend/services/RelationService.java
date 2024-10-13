package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResponse;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResult;
import com.github.jbarus.gradmasterbackend.structures.CorrelationMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class RelationService {


    public ResponseEntity<UploadResponse<List<UUID>>> updateRealitons(UUID id, List<UUID> positiveRelations, List<UUID> negativeRelations) {
        Context context = Context.getInstance(id);
        if(context == null) {
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.UNINITIALIZED_CONTEXT));
        }

        HashMap<UUID,UniversityEmployee> universityEmployeesById = new HashMap<>();
        for(UniversityEmployee universityEmployee : context.getUniversityEmployeeList()){
            universityEmployeesById.put(universityEmployee.getId(), universityEmployee);
        }

        CorrelationMap<UniversityEmployee> positiveCorrelationMap = new CorrelationMap<>();
        CorrelationMap<UniversityEmployee> negativeCorrelationMap = new CorrelationMap<>();

        for (int i = 0; i < positiveRelations.size(); i += 2) {
            positiveCorrelationMap.addRelation(universityEmployeesById.get(positiveRelations.get(i)),universityEmployeesById.get(positiveRelations.get(i+1)));
        }
        for (int i = 0; i < negativeRelations.size(); i += 2) {
            negativeCorrelationMap.addRelation(universityEmployeesById.get(negativeRelations.get(i)),universityEmployeesById.get(negativeRelations.get(i+1)));
        }
        context.setPositiveCorrelation(positiveCorrelationMap);
        context.setNegativeCorrelation(negativeCorrelationMap);
        return ResponseEntity.ok().body(new UploadResponse<>(UploadResult.SUCCESS));
    }
}
