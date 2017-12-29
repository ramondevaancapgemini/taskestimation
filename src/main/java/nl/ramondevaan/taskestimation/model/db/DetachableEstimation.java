package nl.ramondevaan.taskestimation.model.db;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.service.EstimationService;
import org.apache.wicket.model.LoadableDetachableModel;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DetachableEstimation extends LoadableDetachableModel<Estimation> {
    private final           long              id;
    private final transient EstimationService service;

    @Override
    protected Estimation load() {
        return service.getEstimation(this.id);
    }
}
