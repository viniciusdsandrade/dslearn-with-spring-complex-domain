package com.restful.dslearn.dto;

import java.time.Instant;

public record TaskDTO(
        Long id,
        String title,
        Integer position,
        Long sectionId,
        String description,
        Integer questionCount,
        Integer approvalCount,
        Double weight,
        Instant dueDate
) {
}
