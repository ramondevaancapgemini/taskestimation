package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.model.view.task.TaskAdd;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public TaskService() {
        modelMapper = new ModelMapper();
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Could not find task with id %d", id)));
    }

    public void addTask(TaskAdd d) {
        Task task = modelMapper.map(d, Task.class);
        task.setCreated(Instant.now());
        taskRepository.save(task);
    }

    public void removeTask(long id) {
        taskRepository.deleteById(id);
    }
}
