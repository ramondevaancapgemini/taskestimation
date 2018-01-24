package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.provider.SortableTaskDataProvider;
import nl.ramondevaan.taskestimation.service.TaskService;
import nl.ramondevaan.taskestimation.utility.OrderBy;
import nl.ramondevaan.taskestimation.web.extension.SemanticNumEntriesPicker;
import nl.ramondevaan.taskestimation.web.extension.SemanticPagingNavigator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;

public class TaskIndex extends Panel {
    @Inject
    private TaskService service;

    public TaskIndex(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        SortableTaskDataProvider dp = new SortableTaskDataProvider(service);
        DataView<Task> dataView = new DataView<Task>("rows", dp) {
            @Override
            protected void populateItem(Item<Task> item) {
                RepeatingView repeatingView = new RepeatingView("dataRow");

                repeatingView.add(getCell(
                        repeatingView.newChildId(),
                        item.getModel(),
                        new PropertyModel<>(item.getModel(), "name")
                ));
                repeatingView.add(getCell(
                        repeatingView.newChildId(),
                        item.getModel(),
                        new PropertyModel<>(item.getModel(), "description")
                ));

                Link deleteLink = new Link("deleteAction") {
                    @Override
                    public void onClick() {
                        service.removeTask(item.getModelObject().getId());
                    }
                };
                item.add(deleteLink);

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

    private AbstractItem getCell(String id, IModel<Task> t, IModel<?> value) {
        AbstractItem item  = new AbstractItem(id);
        Label        label = new Label("cellValue", value);
        Link link = new Link("cellLink") {
            @Override
            public void onClick() {
                setResponsePage(new TaskEditPage(t));
            }
        };

        item.add(link);
        link.add(label);

        return item;
    }
}
