package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.view.Estimation.EstimationAdd;
import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import nl.ramondevaan.taskestimation.repository.EstimationRepository;
import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimationService {
    @Autowired private EstimationRepository estimationRepository;
    @Autowired private DeveloperRepository developerRepository;
    @Autowired private TaskRepository taskRepository;

    private ModelMapper modelMapper;

    public EstimationService() {
        modelMapper = new ModelMapper();
    }

    public Iterable<Estimation> getAllEstimations() {
        return estimationRepository.findAll();
    }

    public Estimation getEstimation(long id) {
        return estimationRepository.findOne(id);
    }

    public void addEstimation(EstimationAdd e) {
        Estimation t = modelMapper.map(e, Estimation.class);

        t.setDeveloper(developerRepository.findOne(e.getDeveloperId()));
        t.setTask(taskRepository.findOne(e.getTaskId()));

        estimationRepository.save(t);
    }

    public void removeEstimation(long id) {
        estimationRepository.delete(id);
    }
}