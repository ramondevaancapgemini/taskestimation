package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.repository.EstimationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EstimationServiceTest {
    @Mock
    private EstimationRepository estimationRepository;

    @InjectMocks
    private EstimationService estimationService;

    @Test
    public void getAllEstimations() {
        final int numEst = 10;

        List<Estimation> estimations = new ArrayList<>();

        for (int i = 0; i < numEst; i++) {
            Estimation e = mock(Estimation.class);

            estimations.add(e);
        }

        when(estimationRepository.findAll()).thenReturn(estimations);

        Assert.assertEquals(estimations, estimationService.getAllEstimations()
                                                          .collect(Collectors.toList()));
    }

    @Test
    public void getEstimation() {
        Estimation e = mock(Estimation.class);

        final long id = 1;

        when(estimationRepository.findById(id)).thenReturn(Optional.of(e));

        Assert.assertEquals(estimationService.getEstimation(id), e);
    }

    @Test
    public void addEstimation() {
        Estimation e = mock(Estimation.class);

        ArgumentCaptor<Estimation> captor = ArgumentCaptor
                .forClass(Estimation.class);

        estimationService.addEstimation(e);

        verify(estimationRepository, times(1)).save(captor.capture());

        Assert.assertEquals(captor.getValue(), e);
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
