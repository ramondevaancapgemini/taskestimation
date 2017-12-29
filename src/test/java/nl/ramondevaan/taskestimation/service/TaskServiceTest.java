package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        final int numDev = 10;

        for (int i = 0; i < numDev; i++) {
            Task t = mock(Task.class);

            tasks.add(t);
        }

        when(taskRepository.findAll()).thenReturn(tasks);

        Assert.assertEquals(
                tasks,
                taskService.getAllTasks().collect(Collectors.toList())
        );
    }

    @Test
    public void getTask() {
        Task     d = mock(Task.class);

        final long id = 1;

        when(taskRepository.findById(id)).thenReturn(Optional.of(d));

        Assert.assertEquals(d, taskService.getTask(id));
    }

    @Test
    public void addTask() {
        Task d = mock(Task.class);

        taskService.addTask(d);
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(captor.capture());

        Assert.assertEquals(d, captor.getValue());
    }

    @Test
    public void removeTask() {
        final long id = 1;

        taskService.removeTask(id);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(taskRepository, times(1)).deleteById(captor.capture());

        Assert.assertEquals(captor.getValue().longValue(), id);
    }
}
