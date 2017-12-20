package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.view.developer.DeveloperEdit;
import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
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

@RunWith(MockitoJUnitRunner.class) public class DeveloperServiceTest {
    @Mock private DeveloperRepository developerRepository;
    @Mock private ModelMapper modelMapper;

    @InjectMocks private DeveloperService developerService;

    @Test public void getAllDevelopers() {
        List<Developer> developers = new ArrayList<>();

        final int numDev = 10;

        for (int i = 0; i < numDev; i++) {
            developers.add(mock(Developer.class));
        }

        when(developerRepository.findAll()).thenReturn(developers);

        Assert.assertEquals(developers, developerService.getAllDevelopers());
    }

    @Test public void getDeveloper() {
        Developer d = mock(Developer.class);

        final long id = 1;
        when(developerRepository.findOne(id)).thenReturn(d);
        Assert.assertEquals(d, developerService.getDeveloper(id));
    }

    @Test public void addDeveloper() {
        DeveloperEdit da = mock(DeveloperEdit.class);
        Developer     d  = mock(Developer.class);

        when(modelMapper.map(da, Developer.class)).thenReturn(d);

        developerService.addDeveloper(da);
        ArgumentCaptor<Developer> captor = ArgumentCaptor
                .forClass(Developer.class);
        verify(developerRepository, times(1)).save(captor.capture());

        Assert.assertEquals(d, captor.getValue());
    }

    @Test public void removeDeveloper() {
        final long id = 1;

        developerService.removeDeveloper(id);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(developerRepository, times(1)).delete(captor.capture());

        Assert.assertEquals(captor.getValue().longValue(), id);
    }
}
