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

        context.setPositiveCorrelation(positiveRelations);
        context.setNegativeCorrelation(negativeRelations);
        return ResponseEntity.ok().body(new UploadResponse<>(UploadResult.SUCCESS));
    }
}
