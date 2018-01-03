package nl.ramondevaan.taskestimation.model.view;

import lombok.Data;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class EstimationRow implements Serializable {
    private final Task                    task;
    private final Map<Developer, Integer> estimations;

    public final static EstimationRow create(Task t) {
        return new EstimationRow(t, Collections.unmodifiableMap(
                t.getEstimations()
                 .stream()
                 .collect(Collectors.toMap(
                         Estimation::getDeveloper,
                         Estimation::getValue
                 ))
        ));
    }
}
