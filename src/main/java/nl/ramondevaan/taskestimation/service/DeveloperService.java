package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.view.developer.DeveloperAdd;
import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class DeveloperService {
    @Autowired private DeveloperRepository developerRepository;
    private ModelMapper modelMapper;

    public DeveloperService() {
        modelMapper = new ModelMapper();
    }

    public Iterable<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloper(long id) {
        return developerRepository.findOne(id);
    }

    public void addDeveloper(DeveloperAdd d) {
        developerRepository.save(modelMapper.map(d, Developer.class));
    }

    public void removeDeveloper(long id) {
        developerRepository.delete(id);
    }
}
