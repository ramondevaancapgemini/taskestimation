package nl.ramondevaan.taskestimation.model.db;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.model.LoadableDetachableModel;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DetachableTask extends LoadableDetachableModel<Task> {
    private final           Long        id;
    private final transient TaskService service;

    @Override
    protected Task load() {
        return id == null ? new Task() :
                service.getTask(this.id);
    }
}
