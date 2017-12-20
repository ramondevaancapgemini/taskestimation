package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.model.domain.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

@RunWith(SpringRunner.class) @SpringBootTest public class TaskRepositoryTest {
    @Autowired private TaskRepository taskRepository;

    private Instant instant = Instant
            .from(LocalDateTime.of(2017, Month.AUGUST, 20, 15, 37, 23));
    private Task task;
    private boolean isSetUp;

    @Before public void setUp() {
        task = new Task();
        task.setCreated(instant);
        task.setName("Test");
        task.setDescription("TestDescription");
        task.setEstimations(Collections.emptyList());
    }

    @Test public void addTask() {
        Task temp = taskRepository.save(task);

        Long id = temp.getId();
        Assert.assertNotNull(id);

        Task ret = taskRepository.findOne(id);
        Assert.assertEquals(task, ret);

        taskRepository.delete(id);
    }

    @Test public void removeTask() {
        Long id = taskRepository.save(task).getId();

        Assert.assertNotNull(taskRepository.findOne(id));

        taskRepository.delete(id);

        Assert.assertNull(taskRepository.findOne(id));
    }
}
