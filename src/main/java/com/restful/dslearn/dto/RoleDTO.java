package com.restful.dslearn.dto;

import java.util.List;

public record RoleDTO(
        Long id,
        String authority,
        List<Long> userIds
) {
}
