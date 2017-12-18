package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperService {
    @Autowired
    private DeveloperRepository developerRepository;
}
