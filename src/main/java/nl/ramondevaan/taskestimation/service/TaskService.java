package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public TaskService() {

    }

    public Stream<Task> getAllTasks() {
        return taskRepository.findAll().stream();
    }

    public Page<Task> getTasks(Pageable p) {
        return taskRepository.findAll(p);
    }

    public Task getTask(long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Could not find task with id %d",
                        id
                )));
    }

    public long count() {
        return taskRepository.count();
    }

    public void addTask(Task d) {
        d.setCreated(Instant.now());
        taskRepository.save(d);
    }

    public void removeTask(long id) {
        taskRepository.deleteById(id);
    }
}
