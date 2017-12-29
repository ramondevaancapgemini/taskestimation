package nl.ramondevaan.taskestimation.provider;

import nl.ramondevaan.taskestimation.model.db.DetachableEstimation;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.service.EstimationService;
import nl.ramondevaan.taskestimation.utility.OffsetBasedPageRequest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Iterator;

public class SortableEstimationDataProvider extends SortableDataProvider<Estimation, String> {
    private EstimationService service;

    public SortableEstimationDataProvider(EstimationService service) {
        this.service = service;

        setSort("value", SortOrder.ASCENDING);
    }

    @Override
    public Iterator<? extends Estimation> iterator(long first, long count) {
        return service.getEstimations(new OffsetBasedPageRequest(
                (int) first,
                (int) count,
                getJpaSort()
        )).iterator();
    }

    @Override
    public long size() {
        return service.count();
    }

    @Override
    public IModel<Estimation> model(Estimation object) {
        return new DetachableEstimation(object.getId(), service);
    }

    private Sort getJpaSort() {
        SortParam<String> sort = getSort();
        if (sort == null) {
            return Sort.unsorted();
        }
        String            prop = sort.getProperty();

        return prop == null ?
                Sort.unsorted() :
                new Sort(
                        sort.isAscending() ?
                                Sort.Direction.ASC :
                                Sort.Direction.DESC,
                        prop
                );
    }
}