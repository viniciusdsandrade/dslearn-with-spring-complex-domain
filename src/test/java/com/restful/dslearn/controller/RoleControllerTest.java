package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.RoleDTO;
import com.restful.dslearn.dto.RoleRequestDTO;
import com.restful.dslearn.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private RoleService roleService;

    @Test
    void shouldReturnRoles() throws Exception {
        when(roleService.findAll()).thenReturn(List.of(
                new RoleDTO(1L, "ROLE_ADMIN", List.of(1L))));

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].authority", is("ROLE_ADMIN")));
    }

    @Test
    void shouldReturnNotFoundWhenRoleMissing() throws Exception {
        when(roleService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/roles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateRole() throws Exception {
        RoleRequestDTO request = new RoleRequestDTO("ROLE_NEW", List.of(1L));
        when(roleService.create(eq(request)))
                .thenReturn(new RoleDTO(2L, "ROLE_NEW", List.of(1L)));

        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.authority", is("ROLE_NEW")));
    }

    @Test
    void shouldRejectInvalidRole() throws Exception {
        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authority\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
