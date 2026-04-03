package com.restful.dslearn.service;

import com.restful.dslearn.dto.NotificationDTO;
import com.restful.dslearn.dto.NotificationRequestDTO;
import com.restful.dslearn.entity.Notification;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.NotificationRepository;
import com.restful.dslearn.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public List<NotificationDTO> findAll() {
        return notificationRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public NotificationDTO findById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));
        return toDTO(notification);
    }

    @Transactional
    public NotificationDTO create(NotificationRequestDTO request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Notification notification = new Notification();
        notification.setText(request.text());
        notification.setReading(request.reading());
        notification.setRoute(request.route());
        notification.setUser(user);
        return toDTO(notificationRepository.save(notification));
    }

    @Transactional
    public NotificationDTO update(Long id, NotificationRequestDTO request) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        notification.setText(request.text());
        notification.setReading(request.reading());
        notification.setRoute(request.route());
        notification.setUser(user);
        return toDTO(notification);
    }

    @Transactional
    public void delete(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");
        }
        notificationRepository.deleteById(id);
    }

    private NotificationDTO toDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getText(),
                notification.getMoment(),
                notification.getReading(),
                notification.getRoute(),
                notification.getUser().getId()
        );
    }
}
