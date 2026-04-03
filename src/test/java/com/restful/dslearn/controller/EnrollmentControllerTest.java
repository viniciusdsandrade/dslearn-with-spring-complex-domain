package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.EnrollmentDTO;
import com.restful.dslearn.dto.EnrollmentRequestDTO;
import com.restful.dslearn.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(EnrollmentController.class)
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    void shouldReturnEnrollmentByCompositeKey() throws Exception {
        when(enrollmentService.findById(1L, 2L))
                .thenReturn(new EnrollmentDTO(1L, 2L, true, false));

        mockMvc.perform(get("/api/enrollments/user/1/offer/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.offerId", is(2)))
                .andExpect(jsonPath("$.available", is(true)));
    }

    @Test
    void shouldCreateEnrollment() throws Exception {
        EnrollmentRequestDTO request = new EnrollmentRequestDTO(4L, 5L, true, true);
        EnrollmentDTO response = new EnrollmentDTO(4L, 5L, true, true);
        when(enrollmentService.create(eq(request))).thenReturn(response);

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is(4)))
                .andExpect(jsonPath("$.offerId", is(5)));
    }

    @Test
    void shouldDeleteEnrollment() throws Exception {
        mockMvc.perform(delete("/api/enrollments/user/1/offer/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundForCompositeLookup() throws Exception {
        when(enrollmentService.findById(9L, 9L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        mockMvc.perform(get("/api/enrollments/user/9/offer/9"))
                .andExpect(status().isNotFound());
    }
}
