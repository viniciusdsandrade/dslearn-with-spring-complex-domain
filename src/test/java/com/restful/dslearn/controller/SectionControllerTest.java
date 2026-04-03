package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.SectionDTO;
import com.restful.dslearn.dto.SectionRequestDTO;
import com.restful.dslearn.service.SectionService;
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

@WebMvcTest(SectionController.class)
class SectionControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private SectionService sectionService;

    @Test
    void shouldReturnSections() throws Exception {
        when(sectionService.findAll()).thenReturn(List.of(
                new SectionDTO(1L, "Getting Started", "desc", 1, null, 1L, null)));

        mockMvc.perform(get("/api/sections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Getting Started")));
    }

    @Test
    void shouldReturnNotFoundWhenSectionMissing() throws Exception {
        when(sectionService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/sections/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateSection() throws Exception {
        SectionRequestDTO request = new SectionRequestDTO("OOP", "desc", 2, null, 1L, null);
        when(sectionService.create(eq(request)))
                .thenReturn(new SectionDTO(2L, "OOP", "desc", 2, null, 1L, null));

        mockMvc.perform(post("/api/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    void shouldRejectInvalidSection() throws Exception {
        mockMvc.perform(post("/api/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"no title\"}"))
                .andExpect(status().isBadRequest());
    }
}
