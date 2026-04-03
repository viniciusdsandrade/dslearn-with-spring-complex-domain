package com.restful.dslearn.service;

import com.restful.dslearn.dto.NotificationDTO;
import com.restful.dslearn.dto.NotificationRequestDTO;
import com.restful.dslearn.entity.Notification;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.NotificationRepository;
import com.restful.dslearn.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldReturnAllNotifications() {
        User user = new User();
        user.setId(1L);
        Notification n = new Notification();
        n.setId(1L);
        n.setText("Welcome!");
        n.setReading(false);
        n.setRoute("/home");
        n.setUser(user);
        when(notificationRepository.findAll()).thenReturn(List.of(n));

        List<NotificationDTO> result = notificationService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).text()).isEqualTo("Welcome!");
        assertThat(result.get(0).userId()).isEqualTo(1L);
    }

    @Test
    void shouldFindNotificationById() {
        User user = new User();
        user.setId(1L);
        Notification n = new Notification();
        n.setId(1L);
        n.setText("Test");
        n.setReading(true);
        n.setRoute("/test");
        n.setUser(user);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(n));

        NotificationDTO result = notificationService.findById(1L);

        assertThat(result.reading()).isTrue();
    }

    @Test
    void shouldThrowNotFoundWhenNotificationMissing() {
        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificationService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateNotification() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(inv -> {
            Notification saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        NotificationDTO result = notificationService.create(
                new NotificationRequestDTO("New alert", false, "/alerts", 1L));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.text()).isEqualTo("New alert");
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void shouldThrowNotFoundWhenUserMissingOnCreate() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificationService.create(
                new NotificationRequestDTO("X", false, "/x", 99L)))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldUpdateNotification() {
        User user = new User();
        user.setId(1L);
        Notification n = new Notification();
        n.setId(1L);
        n.setText("Old");
        n.setUser(user);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(n));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        NotificationDTO result = notificationService.update(1L,
                new NotificationRequestDTO("Updated", true, "/updated", 1L));

        assertThat(result.text()).isEqualTo("Updated");
        assertThat(result.reading()).isTrue();
    }

    @Test
    void shouldDeleteNotification() {
        when(notificationRepository.existsById(1L)).thenReturn(true);

        notificationService.delete(1L);

        verify(notificationRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingNotification() {
        when(notificationRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> notificationService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
