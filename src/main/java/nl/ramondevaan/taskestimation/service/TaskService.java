package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.model.view.task.TaskEdit;
import nl.ramondevaan.taskestimation.model.view.task.TaskView;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public TaskService() {
        modelMapper = new ModelMapper();
    }

    public Stream<TaskView> getAllTasks() {
        return StreamSupport
                .stream(taskRepository.findAll().spliterator(), false)
                .map(t -> modelMapper.map(t, TaskView.class));
    }

    public TaskView getTask(long id) {
        return taskRepository.findById(id)
                .map(t -> modelMapper.map(t, TaskView.class)).orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format("Could not find task with id %d",
                                        id
                                )));
    }

    public void addTask(TaskEdit d) {
        Task task = modelMapper.map(d, Task.class);
        task.setCreated(Instant.now());
        taskRepository.save(task);
    }

    public void removeTask(long id) {
        taskRepository.deleteById(id);
    }
}
