package com.restful.dslearn.service;

import com.restful.dslearn.dto.TaskDTO;
import com.restful.dslearn.dto.TaskRequestDTO;
import com.restful.dslearn.entity.Section;
import com.restful.dslearn.entity.Task;
import com.restful.dslearn.repository.SectionRepository;
import com.restful.dslearn.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private TaskService taskService;

    private final Instant dueDate = Instant.parse("2025-03-10T23:59:59Z");

    @Test
    void shouldReturnAllTasks() {
        Section section = new Section();
        section.setId(1L);
        Task task = new Task(1L, "Exercises", 1, section, "desc", 10, 7, 1.0, dueDate);
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).questionCount()).isEqualTo(10);
    }

    @Test
    void shouldFindTaskById() {
        Section section = new Section();
        section.setId(1L);
        Task task = new Task(1L, "Quiz", 1, section, "quiz desc", 8, 6, 1.5, dueDate);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.findById(1L);

        assertThat(result.weight()).isEqualTo(1.5);
    }

    @Test
    void shouldThrowNotFoundWhenTaskMissing() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateTask() {
        Section section = new Section();
        section.setId(1L);
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> {
            Task saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        TaskDTO result = taskService.create(
                new TaskRequestDTO("New Task", 1, 1L, "desc", 5, 3, 2.0, dueDate));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.description()).isEqualTo("desc");
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldUpdateTask() {
        Section section = new Section();
        section.setId(1L);
        Task task = new Task(1L, "Old", 1, section, "old", 5, 3, 1.0, dueDate);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));

        TaskDTO result = taskService.update(1L,
                new TaskRequestDTO("Updated", 2, 1L, "new desc", 10, 8, 3.0, dueDate));

        assertThat(result.title()).isEqualTo("Updated");
        assertThat(result.weight()).isEqualTo(3.0);
    }

    @Test
    void shouldDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.delete(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingTask() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> taskService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
