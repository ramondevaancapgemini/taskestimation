package nl.ramondevaan.taskestimation.model.view.Estimation;

import lombok.Data;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Task;

@Data
public class EstimationView {
    private int value;
    private Developer developer;
    private Task task;
}
