package com.restful.dslearn.dto;

import com.restful.dslearn.entity.ResourceType;

public record ResourceDTO(
        Long id,
        String title,
        String description,
        Integer position,
        String imgUri,
        ResourceType type,
        Long offerId
) {
}
