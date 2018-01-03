package nl.ramondevaan.taskestimation.repository;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation, Long> {
}
