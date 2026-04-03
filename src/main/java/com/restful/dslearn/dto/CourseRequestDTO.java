package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseRequestDTO(
        @NotBlank String name,
        String imgUri,
        String imgGrayUri
) {
}
