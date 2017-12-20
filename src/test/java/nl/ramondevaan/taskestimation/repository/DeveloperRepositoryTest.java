package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.model.domain.Developer;
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

@RunWith(SpringRunner.class) @SpringBootTest public class DeveloperRepositoryTest {
    @Autowired private DeveloperRepository developerRepository;

    private Instant instant = Instant
            .from(LocalDateTime.of(2017, Month.AUGUST, 20, 15, 37, 23));
    private Developer developer;

    @Before public void setUp() {
        developer = new Developer();
        developer.setCreated(instant);
        developer.setGivenName("Test");
        developer.setSurnamePrefix("Test");
        developer.setSurname("Test");
        developer.setEmail("test@test.test");
        developer.setEstimations(Collections.emptyList());
    }

    @Test public void addTask() {
        Developer temp = developerRepository.save(developer);

        Long id = temp.getId();
        Assert.assertNotNull(id);

        Developer ret = developerRepository.findOne(id);
        Assert.assertEquals(developer, ret);

        developerRepository.delete(id);
    }

    @Test public void removeTask() {
        Long id = developerRepository.save(developer).getId();

        Assert.assertNotNull(developerRepository.findOne(id));

        developerRepository.delete(id);

        Assert.assertNull(developerRepository.findOne(id));
    }
}
