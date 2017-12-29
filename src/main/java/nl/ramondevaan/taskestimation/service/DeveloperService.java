package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class DeveloperService {
    @Autowired
    private DeveloperRepository developerRepository;

    public DeveloperService() {
    }

    public Stream<Developer> getAllDevelopers() {
        return developerRepository.findAll().stream();
    }

    public Page<Developer> getDevelopers(Pageable pageable) {
        return developerRepository.findAll(pageable);
    }

    public Developer getDeveloper(long id) {
        return developerRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Could not find developer with id %d",
                        id
                )));
    }

    public long count() {
        return developerRepository.count();
    }

    public void addDeveloper(Developer d) {
        d.setCreated(Instant.now());
        developerRepository.save(d);
    }

    public void removeDeveloper(long id) {
        developerRepository.deleteById(id);
    }
}
