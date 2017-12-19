package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.model.view.task.TaskAdd;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public TaskService() {
        modelMapper = new ModelMapper();
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(long id) {
        return taskRepository.findOne(id);
    }

    public void addTask(TaskAdd d) {
        taskRepository.save(modelMapper.map(d, Task.class));
    }

    public void removeTask(long id) {
        taskRepository.delete(id);
    }
}
