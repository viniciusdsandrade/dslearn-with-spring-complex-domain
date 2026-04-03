package com.restful.dslearn.dto;

import com.restful.dslearn.entity.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResourceRequestDTO(
        @NotBlank String title,
        String description,
        Integer position,
        String imgUri,
        @NotNull ResourceType type,
        @NotNull Long offerId
) {
}
