package com.restful.dslearn.dto;

import com.restful.dslearn.entity.DeliverStatus;

public record DeliverDTO(
        Long id,
        String uri,
        String feedback,
        Integer correctCount,
        DeliverStatus status,
        Long lessonId,
        Long enrollmentUserId,
        Long enrollmentOfferId
) {
}
