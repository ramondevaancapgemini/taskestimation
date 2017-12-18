package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.repository.EstimationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimationService {
    @Autowired
    private EstimationRepository estimationRepository;
}
