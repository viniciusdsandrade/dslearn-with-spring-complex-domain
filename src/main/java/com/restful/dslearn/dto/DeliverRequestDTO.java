package com.restful.dslearn.dto;

import com.restful.dslearn.entity.DeliverStatus;
import jakarta.validation.constraints.NotNull;

public record DeliverRequestDTO(
        String uri,
        String feedback,
        Integer correctCount,
        DeliverStatus status,
        @NotNull Long lessonId,
        @NotNull Long enrollmentUserId,
        @NotNull Long enrollmentOfferId
) {
}
