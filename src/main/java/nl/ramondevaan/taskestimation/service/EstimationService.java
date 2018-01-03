package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.repository.EstimationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.stream.Stream;

@Service
public class EstimationService {
    @Autowired
    private EstimationRepository estimationRepository;

    public EstimationService() {

    }

    public Stream<Estimation> getAllEstimations() {
        return estimationRepository.findAll().stream();
    }

    public Estimation getEstimation(long id) {
        return estimationRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Could not find estimation with id %d", id
                )));
    }

    public Page<Estimation> getEstimations(Pageable p) {
        return estimationRepository.findAll(p);
    }

    public long count() {
        return estimationRepository.count();
    }

    public void addEstimation(Estimation e) {
        e.setCreated(Instant.now());
        estimationRepository.save(e);
    }

    public void removeEstimation(long id) {
        estimationRepository.deleteById(id);
    }
}
