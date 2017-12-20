package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.model.view.Estimation.EstimationAdd;
import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import nl.ramondevaan.taskestimation.repository.EstimationRepository;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class EstimationServiceTest {
    @Mock private EstimationRepository estimationRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private DeveloperRepository developerRepository;
    @Mock private ModelMapper modelMapper;

    @InjectMocks private EstimationService estimationService;

    @Test public void getAllEstimations() {
        final int numEst = 10;

        List<Estimation> estimations = new ArrayList<>();

        for (int i = 0; i < numEst; i++) {
            estimations.add(mock(Estimation.class));
        }

        when(estimationRepository.findAll()).thenReturn(estimations);

        Assert.assertEquals(estimationService.getAllEstimations(), estimations);
    }

    @Test public void getEstimation() {
        Estimation e = mock(Estimation.class);

        final long id = 1;

        when(estimationRepository.findOne(id)).thenReturn(e);
        Assert.assertEquals(estimationService.getEstimation(id), e);
    }

    @Test public void addEstimation() {
        final long developerId = 1;
        final long taskId      = 5;

        Developer     d   = mock(Developer.class);
        Task          t   = mock(Task.class);
        Estimation    e   = mock(Estimation.class);
        EstimationAdd add = mock(EstimationAdd.class);

        ArgumentCaptor<Long> taskCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> developerCaptor = ArgumentCaptor
                .forClass(Long.class);
        ArgumentCaptor<Estimation> captor = ArgumentCaptor
                .forClass(Estimation.class);

        when(add.getDeveloperId()).thenReturn(developerId);
        when(add.getTaskId()).thenReturn(taskId);
        when(developerRepository.findOne(developerId)).thenReturn(d);
        when(taskRepository.findOne(taskId)).thenReturn(t);
        when(modelMapper.map(add, Estimation.class)).thenReturn(e);
        estimationService.addEstimation(add);

        verify(estimationRepository, times(1)).save(captor.capture());
        verify(taskRepository, times(1)).findOne(taskCaptor.capture());
        verify(developerRepository, times(1))
                .findOne(developerCaptor.capture());

        Assert.assertEquals(captor.getValue(), e);
        Assert.assertEquals(taskCaptor.getValue().longValue(), taskId);
        Assert.assertEquals(developerCaptor.getValue().longValue(),
                developerId
        );
    }

    @Test public void removeEstimation() {
        final long id = 1;

        estimationService.removeEstimation(id);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(estimationRepository, times(1)).delete(captor.capture());

        Assert.assertEquals(captor.getValue().longValue(), id);
    }
}
