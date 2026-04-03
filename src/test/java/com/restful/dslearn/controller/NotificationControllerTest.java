package com.restful.dslearn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.dslearn.dto.NotificationDTO;
import com.restful.dslearn.dto.NotificationRequestDTO;
import com.restful.dslearn.service.NotificationService;
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

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private NotificationService notificationService;

    @Test
    void shouldReturnNotifications() throws Exception {
        when(notificationService.findAll()).thenReturn(List.of(
                new NotificationDTO(1L, "Welcome!", null, false, "/home", 1L)));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].text", is("Welcome!")));
    }

    @Test
    void shouldReturnNotFoundWhenNotificationMissing() throws Exception {
        when(notificationService.findById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/notifications/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNotification() throws Exception {
        NotificationRequestDTO request = new NotificationRequestDTO("Alert", false, "/alerts", 1L);
        when(notificationService.create(eq(request)))
                .thenReturn(new NotificationDTO(2L, "Alert", null, false, "/alerts", 1L));

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is("Alert")));
    }

    @Test
    void shouldRejectInvalidNotification() throws Exception {
        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"route\":\"/x\"}"))
                .andExpect(status().isBadRequest());
    }
}
