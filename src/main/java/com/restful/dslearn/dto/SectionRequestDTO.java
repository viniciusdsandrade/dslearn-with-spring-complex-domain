package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SectionRequestDTO(
        @NotBlank String title,
        String description,
        Integer position,
        String imgUri,
        @NotNull Long resourceId,
        Long prerequisiteId
) {
}
