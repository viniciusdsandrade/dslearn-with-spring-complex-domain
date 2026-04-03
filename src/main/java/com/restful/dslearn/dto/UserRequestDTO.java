package com.restful.dslearn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserRequestDTO(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password,
        @NotNull List<Long> roleIds
) {
}
