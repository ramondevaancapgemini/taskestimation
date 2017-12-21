package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.TestConfig;
import nl.ramondevaan.taskestimation.model.domain.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    private Instant instant = Instant.now();
    private Task task;

    @Before
    public void setUp() {
        task = new Task();
        task.setCreated(instant);
        task.setName("Test");
        task.setDescription("TestDescription");
        task.setEstimations(Collections.emptyList());
    }

    @Test
    public void addTask() {
        Task temp = taskRepository.save(task);

        Long id = temp.getId();
        Assert.assertNotNull(id);

        Task ret = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Could not find task with id %d", id)));
        Assert.assertEquals(task, ret);

        taskRepository.deleteById(id);
    }

    @Test
    public void removeTask() {
        Long id = taskRepository.save(task).getId();

        Assert.assertTrue(taskRepository.findById(id).isPresent());

        taskRepository.deleteById(id);

        Assert.assertFalse(taskRepository.findById(id).isPresent());
    }
}
