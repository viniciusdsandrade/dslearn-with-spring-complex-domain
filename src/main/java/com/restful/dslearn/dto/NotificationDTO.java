package com.restful.dslearn.dto;

import java.time.LocalDateTime;

public record NotificationDTO(
        Long id,
        String text,
        LocalDateTime moment,
        Boolean reading,
        String route,
        Long userId
) {
}
