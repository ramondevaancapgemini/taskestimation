package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.model.view.task.TaskAdd;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class) @SpringBootTest public class TaskServiceTest {
    @Mock private TaskRepository taskRepository;
    @Mock private ModelMapper modelMapper;

    @InjectMocks private TaskService taskService;

    @Test public void getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        final int numDev = 10;

        for (int i = 0; i < numDev; i++) {
            tasks.add(mock(Task.class));
        }

        when(taskRepository.findAll()).thenReturn(tasks);

        Assert.assertEquals(tasks, taskService.getAllTasks());
    }

    @Test public void getTask() {
        Task d = mock(Task.class);

        final long id = 1;
        when(taskRepository.findOne(id)).thenReturn(d);
        Assert.assertEquals(d, taskService.getTask(id));
    }

    @Test public void addTask() {
        TaskAdd da = mock(TaskAdd.class);
        Task    d  = mock(Task.class);

        when(modelMapper.map(da, Task.class)).thenReturn(d);

        taskService.addTask(da);
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(captor.capture());

        Assert.assertEquals(d, captor.getValue());
    }

    @Test public void removeTask() {
        final long id = 1;

        taskService.removeTask(id);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(taskRepository, times(1)).delete(captor.capture());

        Assert.assertEquals(captor.getValue().longValue(), id);
    }
}
