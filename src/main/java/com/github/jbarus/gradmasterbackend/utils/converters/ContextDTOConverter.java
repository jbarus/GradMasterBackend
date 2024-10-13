package com.github.jbarus.gradmasterbackend.utils.converters;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.dto.ContextOverviewDTO;

public class ContextDTOConverter {
    public static ContextOverviewDTO convertToDTO(Context context) {
        if (context == null) {
            return null;
        }
        return new ContextOverviewDTO(context.getId(),context.getName(),context.getDate());
    }
}
