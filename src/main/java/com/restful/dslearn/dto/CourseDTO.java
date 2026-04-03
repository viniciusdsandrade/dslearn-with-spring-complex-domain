package com.restful.dslearn.dto;

public record CourseDTO(
        Long id,
        String name,
        String imgUri,
        String imgGrayUri
) {
}
