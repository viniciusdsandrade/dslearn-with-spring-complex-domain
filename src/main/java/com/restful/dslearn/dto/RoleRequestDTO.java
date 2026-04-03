package com.restful.dslearn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RoleRequestDTO(
        @NotBlank String authority,
        @NotNull List<Long> userIds
) {
}
