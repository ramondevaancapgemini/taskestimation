package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.view.developer.DeveloperEdit;
import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;

@Service
public class DeveloperService {
    @Autowired
    private DeveloperRepository developerRepository;
    private ModelMapper modelMapper;

    public DeveloperService() {
        modelMapper = new ModelMapper();
    }

    public Iterable<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloper(long id) {
        return developerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Could not find developer with id %d",
                                id
                        )));
    }

    public void addDeveloper(DeveloperEdit d) {
        Developer dev = modelMapper.map(d, Developer.class);
        dev.setCreated(Instant.now());
        developerRepository.save(dev);
    }

    public void removeDeveloper(long id) {
        developerRepository.deleteById(id);
    }
}
