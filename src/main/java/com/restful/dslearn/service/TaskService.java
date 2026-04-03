package com.restful.dslearn.service;

import com.restful.dslearn.dto.TaskDTO;
import com.restful.dslearn.dto.TaskRequestDTO;
import com.restful.dslearn.entity.Section;
import com.restful.dslearn.entity.Task;
import com.restful.dslearn.repository.SectionRepository;
import com.restful.dslearn.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final SectionRepository sectionRepository;

    public TaskService(TaskRepository taskRepository, SectionRepository sectionRepository) {
        this.taskRepository = taskRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return toDTO(task);
    }

    @Transactional
    public TaskDTO create(TaskRequestDTO request) {
        Section section = sectionRepository.findById(request.sectionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));
        Task task = new Task();
        task.setTitle(request.title());
        task.setPosition(request.position());
        task.setSection(section);
        task.setDescription(request.description());
        task.setQuestionCount(request.questionCount());
        task.setApprovalCount(request.approvalCount());
        task.setWeight(request.weight());
        task.setDueDate(request.dueDate());
        return toDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO update(Long id, TaskRequestDTO request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        Section section = sectionRepository.findById(request.sectionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));
        task.setTitle(request.title());
        task.setPosition(request.position());
        task.setSection(section);
        task.setDescription(request.description());
        task.setQuestionCount(request.questionCount());
        task.setApprovalCount(request.approvalCount());
        task.setWeight(request.weight());
        task.setDueDate(request.dueDate());
        return toDTO(task);
    }

    @Transactional
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        taskRepository.deleteById(id);
    }

    private TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getPosition(),
                task.getSection().getId(),
                task.getDescription(),
                task.getQuestionCount(),
                task.getApprovalCount(),
                task.getWeight(),
                task.getDueDate()
        );
    }
}
