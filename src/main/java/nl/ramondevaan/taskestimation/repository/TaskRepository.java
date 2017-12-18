package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
