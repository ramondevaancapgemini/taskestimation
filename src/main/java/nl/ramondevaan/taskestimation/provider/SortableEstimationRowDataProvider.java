package nl.ramondevaan.taskestimation.provider;

import nl.ramondevaan.taskestimation.model.db.DetachableEstimationRow;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.view.EstimationRow;
import nl.ramondevaan.taskestimation.service.TaskService;
import nl.ramondevaan.taskestimation.utility.OffsetBasedPageRequest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;

public class SortableEstimationRowDataProvider extends SortableDataProvider<EstimationRow, String> {
    private final TaskService taskService;
    private final IModel<List<Developer>> developers;

    public SortableEstimationRowDataProvider(
            TaskService taskService,
            IModel<List<Developer>> developers
    ) {
        this.taskService = taskService;
        this.developers = developers;

        setSort("name", SortOrder.ASCENDING);
    }

    @Override
    public Iterator<? extends EstimationRow> iterator(long first, long count) {
        return taskService
                .getTasks(new OffsetBasedPageRequest(
                        (int) first,
                        (int) count,
                        getJpaSort()
                ))
                .getContent()
                .stream()
                .map(t -> EstimationRow.create(t, developers.getObject()))
                .iterator();
    }

    @Override
    public long size() {
        return taskService.count();
    }

    @Override
    public IModel<EstimationRow> model(EstimationRow object) {
        return new DetachableEstimationRow(object.getTask().getId(), developers, taskService);
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
