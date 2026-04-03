package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.ResourceDTO;
import com.restful.dslearn.dto.ResourceRequestDTO;
import com.restful.dslearn.entity.ResourceType;
import com.restful.dslearn.service.ResourceService;
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

@WebMvcTest(ResourceController.class)
class ResourceControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ResourceService resourceService;

    @Test
    void shouldReturnResources() throws Exception {
        when(resourceService.findAll()).thenReturn(List.of(
                new ResourceDTO(1L, "Intro", "desc", 1, null, ResourceType.LESSON_ONLY, 1L)));

        mockMvc.perform(get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Intro")));
    }

    @Test
    void shouldReturnNotFoundWhenResourceMissing() throws Exception {
        when(resourceService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/resources/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateResource() throws Exception {
        ResourceRequestDTO request = new ResourceRequestDTO("Tasks", "desc", 1, null, ResourceType.LESSON_TASK, 1L);
        when(resourceService.create(eq(request)))
                .thenReturn(new ResourceDTO(2L, "Tasks", "desc", 1, null, ResourceType.LESSON_TASK, 1L));

        mockMvc.perform(post("/api/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    void shouldRejectInvalidResource() throws Exception {
        mockMvc.perform(post("/api/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"no title\"}"))
                .andExpect(status().isBadRequest());
    }
}
