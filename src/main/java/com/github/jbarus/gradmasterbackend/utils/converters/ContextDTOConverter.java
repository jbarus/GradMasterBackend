package com.github.jbarus.gradmasterbackend.utils.converters;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.dto.ContextDTO;

public class ContextDTOConverter {
    public static ContextDTO convertToDTO(Context context) {
        if (context == null) {
            return null;
        }
        return new ContextDTO(context.getId(),context.getName(),context.getDate());
    }
}
