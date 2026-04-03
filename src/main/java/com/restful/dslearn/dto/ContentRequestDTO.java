package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContentRequestDTO(
        @NotBlank String title,
        @NotNull Integer position,
        @NotNull Long sectionId,
        String text,
        String videoUri
) {
}
