package nl.ramondevaan.taskestimation.model.db.Estimation;

import lombok.Data;

@Data
public class EstimationEdit {
    private int value;
    private long developerId;
    private long taskId;
}
