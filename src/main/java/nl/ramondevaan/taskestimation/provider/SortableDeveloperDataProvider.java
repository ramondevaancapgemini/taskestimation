package nl.ramondevaan.taskestimation.provider;

import nl.ramondevaan.taskestimation.model.db.DetachableDeveloper;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.utility.OffsetBasedPageRequest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Iterator;

public class SortableDeveloperDataProvider extends SortableDataProvider<Developer, String> {
    private DeveloperService service;

    public SortableDeveloperDataProvider(DeveloperService service) {
        this.service = service;

        setSort("surname", SortOrder.ASCENDING);
    }

    @Override
    public Iterator<? extends Developer> iterator(long first, long count) {
        return service.getDevelopers(new OffsetBasedPageRequest(
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
    public IModel<Developer> model(Developer object) {
        return new DetachableDeveloper(object.getId(), service);
    }

    private Sort getJpaSort() {
        SortParam<String> sort = getSort();
        if (sort == null) {
            return Sort.unsorted();
        }
        String prop = sort.getProperty();

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
