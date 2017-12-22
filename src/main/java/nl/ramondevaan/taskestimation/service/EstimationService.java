package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.view.Estimation.EstimationEdit;
import nl.ramondevaan.taskestimation.model.view.Estimation.EstimationView;
import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import nl.ramondevaan.taskestimation.repository.EstimationRepository;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EstimationService {
    @Autowired
    private EstimationRepository estimationRepository;
    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private TaskRepository taskRepository;

    private ModelMapper modelMapper;

    public EstimationService() {
        modelMapper = new ModelMapper();
    }

    public Stream<EstimationView> getAllEstimations() {
        return StreamSupport
                .stream(estimationRepository.findAll().spliterator(), false)
                .map(e -> modelMapper.map(e, EstimationView.class));
    }

    public EstimationView getEstimation(long id) {
        return estimationRepository.findById(id)
                .map(e -> modelMapper.map(e, EstimationView.class)).orElseThrow(
                        () -> new EntityNotFoundException(String.format(
                                "Could not find estimation with id %d", id)));
    }

    public void addEstimation(EstimationEdit e) {
        Estimation t = modelMapper.map(e, Estimation.class);

        t.setDeveloper(developerRepository.findById(e.getDeveloperId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Could not find developer with id %d",
                                e.getDeveloperId()
                        ))));
        t.setTask(taskRepository.findById(e.getTaskId()).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Could not find task with id %d",
                                e.getTaskId()
                        ))));
        t.setCreated(Instant.now());

        estimationRepository.save(t);
    }

    public void removeEstimation(long id) {
        estimationRepository.deleteById(id);
    }
}
