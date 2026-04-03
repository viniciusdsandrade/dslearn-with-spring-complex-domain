package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record TaskRequestDTO(
        @NotBlank String title,
        @NotNull Integer position,
        @NotNull Long sectionId,
        String description,
        Integer questionCount,
        Integer approvalCount,
        Double weight,
        Instant dueDate
) {
}
