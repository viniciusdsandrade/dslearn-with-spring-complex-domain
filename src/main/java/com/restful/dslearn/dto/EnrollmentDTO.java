package com.restful.dslearn.dto;

public record EnrollmentDTO(
        Long userId,
        Long offerId,
        Boolean available,
        Boolean onlyUpdate
) {
}
