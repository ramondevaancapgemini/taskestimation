package nl.ramondevaan.taskestimation.model.db;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import org.apache.wicket.model.LoadableDetachableModel;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DetachableDeveloper extends LoadableDetachableModel<Developer> {
    private final           Long             id;
    private final transient DeveloperService service;

    @Override
    protected Developer load() {
        return id == null ? new Developer() :
                service.getDeveloper(this.id);
    }
}
