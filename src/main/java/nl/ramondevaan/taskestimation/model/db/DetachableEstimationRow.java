package nl.ramondevaan.taskestimation.model.db;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.model.view.EstimationRow;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DetachableEstimationRow extends LoadableDetachableModel<EstimationRow> {
    private final           Long            id;
    private final IModel<List<Developer>>   developers;
    private final transient TaskService     taskService;

    @Override
    protected EstimationRow load() {
        return EstimationRow.create(
                id == null ? new Task() :
                        taskService.getTask(id),
                developers.getObject()
        );
    }
}
