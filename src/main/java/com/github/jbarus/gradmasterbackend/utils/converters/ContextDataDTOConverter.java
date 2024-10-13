package com.github.jbarus.gradmasterbackend.utils.converters;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.dto.ContextDataDTO;
import com.github.jbarus.gradmasterbackend.models.dto.ContextOverviewDTO;

public class ContextDataDTOConverter {
    public static ContextDataDTO convertToDTO(Context context) {
        if (context == null) {
            return null;
        }
        return new ContextDataDTO(context.getId(),context.getUniversityEmployeeList(),
                context.getUnassignedStudentList(),context.getPositiveCorrelation(),
                context.getNegativeCorrelation(),context.getContextOptions());
    }
}
