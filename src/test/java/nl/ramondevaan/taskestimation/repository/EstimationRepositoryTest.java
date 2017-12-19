package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

@RunWith(SpringRunner.class) @SpringBootTest public class EstimationRepositoryTest {

    @Autowired private DeveloperRepository developerRepository;
    @Autowired private TaskRepository taskRepository;
    @Autowired private EstimationRepository estimationRepository;

    private LocalDateTime dateTime;
    private Task task;
    private Developer developer;
    private Estimation estimation;
    private boolean isSetUp;

    @Before public void setUp() {
        if (!isSetUp) {
            dateTime = LocalDateTime.of(2017, Month.AUGUST, 20, 15, 37, 23);

            isSetUp = true;
        }
        task = new Task();
        task.setCreated(dateTime);
        task.setName("Test");
        task.setDescription("TestDescription");
        task.setEstimations(Collections.emptyList());

        developer = new Developer();
        developer.setCreated(dateTime);
        developer.setGivenName("Test");
        developer.setSurnamePrefix("Test");
        developer.setSurname("Test");
        developer.setEmail("test@test.test");
        developer.setBirthDate(dateTime);
        developer.setEstimations(Collections.emptyList());

        estimation = new Estimation();
        estimation.setCreated(dateTime);
        estimation.setDeveloper(developer);
        estimation.setTask(task);
        estimation.setValue(2);

        taskRepository.save(task);
        developerRepository.save(developer);
    }

    @Test public void addEstimation() {
        Long id = estimationRepository.save(estimation).getId();

        Assert.assertNotNull(id);

        Assert.assertEquals(estimationRepository.findOne(id), estimation);

        estimationRepository.delete(id);
    }

    @Test public void removeEstimation() {
        Long id = estimationRepository.save(estimation).getId();

        Assert.assertNotNull(estimationRepository.findOne(id));

        estimationRepository.delete(id);

        Assert.assertNull(estimationRepository.findOne(id));
    }

    @Test public void updateEstimation() {
        Long id = estimationRepository.save(estimation).getId();

        int newValue = estimation.getValue() + 1;
        estimation.setValue(newValue);

        estimationRepository.save(estimation);

        Assert.assertEquals(newValue,
                estimationRepository.findOne(id).getValue()
        );

        estimationRepository.delete(id);
    }

    @After public void tearDown() {
        taskRepository.delete(task.getId());
        developerRepository.delete(developer.getId());
    }
}
