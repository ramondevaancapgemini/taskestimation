package nl.ramondevaan.taskestimation.model.db;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.taskestimation.model.view.EstimationRow;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.model.LoadableDetachableModel;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DetachableEstimationRow extends LoadableDetachableModel<EstimationRow> {
    private final           long        id;
    private final transient TaskService service;

    @Override
    protected EstimationRow load() {
        return EstimationRow.create(service.getTask(id));
    }
}
