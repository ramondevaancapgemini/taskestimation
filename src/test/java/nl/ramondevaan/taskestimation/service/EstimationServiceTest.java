package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.model.view.Estimation.EstimationEdit;
import nl.ramondevaan.taskestimation.model.view.Estimation.EstimationView;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EstimationServiceTest {
    @Mock
    private EstimationRepository estimationRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EstimationService estimationService;

    @Test
    public void getAllEstimations() {
        final int numEst = 10;

        List<Estimation>     estimations = new ArrayList<>();
        List<EstimationView> views       = new ArrayList<>();

        for (int i = 0; i < numEst; i++) {
            Estimation     e = mock(Estimation.class);
            EstimationView v = mock(EstimationView.class);

            estimations.add(e);
            views.add(v);

            when(modelMapper.map(e, EstimationView.class)).thenReturn(v);
        }

        when(estimationRepository.findAll()).thenReturn(estimations);

        Assert.assertEquals(views, estimationService.getAllEstimations()
                .collect(Collectors.toList()));
    }

    @Test
    public void getEstimation() {
        Estimation     e = mock(Estimation.class);
        EstimationView v = mock(EstimationView.class);

        final long id = 1;

        when(modelMapper.map(e, EstimationView.class)).thenReturn(v);
        when(estimationRepository.findById(id)).thenReturn(Optional.of(e));

        Assert.assertEquals(estimationService.getEstimation(id), v);
    }

    @Test
    public void addEstimation() {
        final long developerId = 1;
        final long taskId      = 5;

        Developer      d   = mock(Developer.class);
        Task           t   = mock(Task.class);
        Estimation     e   = mock(Estimation.class);
        EstimationEdit add = mock(EstimationEdit.class);

        ArgumentCaptor<Long> taskCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> developerCaptor = ArgumentCaptor
                .forClass(Long.class);
        ArgumentCaptor<Estimation> captor = ArgumentCaptor
                .forClass(Estimation.class);

        when(add.getDeveloperId()).thenReturn(developerId);
        when(add.getTaskId()).thenReturn(taskId);
        when(developerRepository.findById(developerId))
                .thenReturn(Optional.of(d));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(t));
        when(modelMapper.map(add, Estimation.class)).thenReturn(e);
        estimationService.addEstimation(add);

        verify(estimationRepository, times(1)).save(captor.capture());
        verify(taskRepository, times(1)).findById(taskCaptor.capture());
        verify(developerRepository, times(1))
                .findById(developerCaptor.capture());

        Assert.assertEquals(captor.getValue(), e);
        Assert.assertEquals(taskCaptor.getValue().longValue(), taskId);
        Assert.assertEquals(developerCaptor.getValue().longValue(),
                developerId
        );
    }

    @Test
    public void removeEstimation() {
        final long id = 1;

        estimationService.removeEstimation(id);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(estimationRepository, times(1)).deleteById(captor.capture());

        Assert.assertEquals(captor.getValue().longValue(), id);
    }
}
