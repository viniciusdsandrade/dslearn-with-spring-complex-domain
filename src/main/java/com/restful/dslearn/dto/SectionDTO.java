package com.restful.dslearn.dto;

public record SectionDTO(
        Long id,
        String title,
        String description,
        Integer position,
        String imgUri,
        Long resourceId,
        Long prerequisiteId
) {
}
