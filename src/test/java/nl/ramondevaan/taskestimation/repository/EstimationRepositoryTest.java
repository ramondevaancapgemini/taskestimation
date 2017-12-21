package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.TestConfig;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import org.junit.After;
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
public class EstimationRepositoryTest {
    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EstimationRepository estimationRepository;

    private Instant instant = Instant.now();
    private Task task;
    private Developer developer;
    private Estimation estimation;

    @Before
    public void setUp() {
        task = new Task();
        task.setCreated(instant);
        task.setName("Test");
        task.setDescription("TestDescription");
        task.setEstimations(Collections.emptyList());

        developer = new Developer();
        developer.setCreated(instant);
        developer.setGivenName("Test");
        developer.setSurnamePrefix("Test");
        developer.setSurname("Test");
        developer.setEmail("test@test.test");
        developer.setEstimations(Collections.emptyList());

        estimation = new Estimation();
        estimation.setCreated(instant);
        estimation.setDeveloper(developer);
        estimation.setTask(task);
        estimation.setValue(2);

        taskRepository.save(task);
        developerRepository.save(developer);
    }

    @Test
    public void addEstimation() {
        Long id = estimationRepository.save(estimation).getId();

        Assert.assertNotNull(id);

        Assert.assertEquals(estimationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Could not find estimation with id %d",
                                id
                        ))), estimation);

        estimationRepository.deleteById(id);
    }

    @Test
    public void removeEstimation() {
        Long id = estimationRepository.save(estimation).getId();

        Assert.assertTrue(estimationRepository.findById(id).isPresent());

        estimationRepository.deleteById(id);

        Assert.assertFalse(estimationRepository.findById(id).isPresent());
    }

    @Test
    public void updateEstimation() {
        Long id = estimationRepository.save(estimation).getId();

        int newValue = estimation.getValue() + 1;
        estimation.setValue(newValue);

        estimationRepository.save(estimation);

        Assert.assertEquals(newValue, estimationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Could not find estimation with id %d",
                                id
                        ))).getValue());

        estimationRepository.deleteById(id);
    }

    @After
    public void tearDown() {
        taskRepository.deleteById(task.getId());
        developerRepository.deleteById(developer.getId());
    }
}
