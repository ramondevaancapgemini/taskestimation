package nl.ramondevaan.taskestimation.service;

import nl.ramondevaan.taskestimation.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
}
