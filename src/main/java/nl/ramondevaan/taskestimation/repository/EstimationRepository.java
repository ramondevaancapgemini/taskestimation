package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.model.Estimation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimationRepository extends CrudRepository<Estimation, Long> {
}
