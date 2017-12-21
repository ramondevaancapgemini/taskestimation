package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.TestConfig;
import nl.ramondevaan.taskestimation.model.domain.Developer;
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
public class DeveloperRepositoryTest {
    @Autowired
    private DeveloperRepository developerRepository;

    private Instant instant = Instant.now();
    private Developer developer;

    @Before
    public void setUp() {
        developer = new Developer();
        developer.setCreated(instant);
        developer.setGivenName("Test");
        developer.setSurnamePrefix("Test");
        developer.setSurname("Test");
        developer.setEmail("test@test.test");
        developer.setEstimations(Collections.emptyList());
    }

    @Test
    public void addDeveloper() {
        Developer temp = developerRepository.save(developer);

        Long id = temp.getId();
        Assert.assertNotNull(id);

        Developer ret = developerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Could not find developer with id %d",
                                id
                        )));
        Assert.assertEquals(developer, ret);

        developerRepository.deleteById(id);
    }

    @Test
    public void removeDeveloper() {
        Long id = developerRepository.save(developer).getId();

        Assert.assertTrue(developerRepository.findById(id).isPresent());

        developerRepository.deleteById(id);

        Assert.assertFalse(developerRepository.findById(id).isPresent());
    }
}
