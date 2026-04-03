package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.ContentDTO;
import com.restful.dslearn.dto.ContentRequestDTO;
import com.restful.dslearn.service.ContentService;
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

@WebMvcTest(ContentController.class)
class ContentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ContentService contentService;

    @Test
    void shouldReturnContents() throws Exception {
        when(contentService.findAll()).thenReturn(List.of(
                new ContentDTO(1L, "Intro", 1, 1L, "text", "video.mp4")));

        mockMvc.perform(get("/api/contents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Intro")));
    }

    @Test
    void shouldReturnNotFoundWhenContentMissing() throws Exception {
        when(contentService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/contents/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateContent() throws Exception {
        ContentRequestDTO request = new ContentRequestDTO("OOP", 1, 1L, "classes", "oop.mp4");
        when(contentService.create(eq(request)))
                .thenReturn(new ContentDTO(2L, "OOP", 1, 1L, "classes", "oop.mp4"));

        mockMvc.perform(post("/api/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("OOP")));
    }

    @Test
    void shouldRejectInvalidContent() throws Exception {
        mockMvc.perform(post("/api/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"no title\"}"))
                .andExpect(status().isBadRequest());
    }
}
