package com.debayan.taskapi.Task.repository;

import com.debayan.taskapi.Task.entity.Task;
import com.debayan.taskapi.Task.enums.Priority;
import com.debayan.taskapi.Task.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSaveAndFindById() {
        Task task = new Task();
        task.setTitle("Write repository test");
        task.setDescription("Test saving and fetching a task");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(Priority.MEDIUM);
        task.setDueDate(LocalDate.now());

        Task saved = taskRepository.save(task);

        Optional<Task> found = taskRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Write repository test");
    }

    @Test
    void testFindById_NotFound() {
        Optional<Task> found = taskRepository.findById(123L);
        assertThat(found).isEmpty();
    }
}
