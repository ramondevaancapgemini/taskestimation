package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import nl.ramondevaan.taskestimation.repository.EstimationRepository;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) @SpringBootTest public class EstimationServiceTest {
    @Mock private EstimationRepository estimationRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private DeveloperRepository developerRepository;

    @InjectMocks private EstimationService estimationService;
}
