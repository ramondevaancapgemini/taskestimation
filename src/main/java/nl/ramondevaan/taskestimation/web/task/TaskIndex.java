package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.provider.SortableTaskDataProvider;
import nl.ramondevaan.taskestimation.service.TaskService;
import nl.ramondevaan.taskestimation.utility.OrderBy;
import nl.ramondevaan.taskestimation.web.extension.SemanticNumEntriesPicker;
import nl.ramondevaan.taskestimation.web.extension.SemanticPagingNavigator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;

import javax.inject.Inject;

public class TaskIndex extends Panel {
    @Inject
    private TaskService service;

    public TaskIndex(String id) {
        super(id);

        SortableTaskDataProvider dp = new SortableTaskDataProvider(service);
        DataView<Task> dataView = new DataView<Task>("rows", dp) {
            @Override
            protected void populateItem(Item<Task> item) {
                Task     view          = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRow");
                repeatingView.add(new Label(
                        repeatingView.newChildId(),
                        view.getName()
                ));
                repeatingView.add(new Label(
                        repeatingView.newChildId(),
                        view.getDescription()
                ));
                item.add(repeatingView);
            }
        };

        add(new OrderBy(
                "orderByName",
                "name",
                dp,
                () -> dataView.setCurrentPage(0)
        ));
        add(new OrderBy(
                "orderByDescription",
                "description",
                dp,
                () -> dataView.setCurrentPage(0)
        ));

        add(dataView);

        add(new SemanticPagingNavigator("navigator", dataView));
        add(new SemanticNumEntriesPicker("numEntries", dataView));
    }
}
