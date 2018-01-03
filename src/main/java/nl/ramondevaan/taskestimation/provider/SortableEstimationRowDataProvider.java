package nl.ramondevaan.taskestimation.provider;

import nl.ramondevaan.taskestimation.model.db.DetachableEstimationRow;
import nl.ramondevaan.taskestimation.model.view.EstimationRow;
import nl.ramondevaan.taskestimation.service.EstimationService;
import nl.ramondevaan.taskestimation.service.TaskService;
import nl.ramondevaan.taskestimation.utility.OffsetBasedPageRequest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.springframework.data.domain.Sort;

import java.util.Iterator;

public class SortableEstimationRowDataProvider extends SortableDataProvider<EstimationRow, String> {
    private final TaskService       taskService;

    public SortableEstimationRowDataProvider(TaskService taskService) {
        this.taskService = taskService;

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
                .map(EstimationRow::create)
                .iterator();
    }

    @Override
    public long size() {
        return taskService.count();
    }

    @Override
    public IModel<EstimationRow> model(EstimationRow object) {
        return new DetachableEstimationRow(object.getTask().getId(), taskService);
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
