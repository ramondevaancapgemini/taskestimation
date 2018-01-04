package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.view.EstimationRow;
import nl.ramondevaan.taskestimation.provider.SortableEstimationRowDataProvider;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.service.EstimationService;
import nl.ramondevaan.taskestimation.service.TaskService;
import nl.ramondevaan.taskestimation.utility.OrderBy;
import nl.ramondevaan.taskestimation.web.extension.SemanticNumEntriesPicker;
import nl.ramondevaan.taskestimation.web.extension.SemanticPagingNavigator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class EstimationIndex extends Panel {
    @Inject
    private DeveloperService  developerService;
    @Inject
    private TaskService       taskService;
    @Inject
    private EstimationService estimationService;

    public EstimationIndex(String id) {
        super(id);

        List<Developer> developers = developerService
                .getAllDevelopers()
                .collect(Collectors.toList());

        RepeatingView headers = new RepeatingView("headerRow");

        for (Developer d : developers) {
            headers.add(new Label(
                    headers.newChildId(),
                    d.getFullName()
            ));
        }

        add(headers);

        SortableEstimationRowDataProvider dp =
                new SortableEstimationRowDataProvider(taskService);
        DataView<EstimationRow> dataView = new DataView<EstimationRow>("rows", dp) {
            @Override
            protected void populateItem(Item<EstimationRow> item) {
                EstimationRow view          = item.getModelObject();

                item.add(new Label(
                        "taskName",
                        view.getTask().getName()
                ));

                RepeatingView repeatingView = new RepeatingView("dataRow");

                ValueGetter g;
                if (developers.stream()
                              .allMatch(view.getEstimations()::containsKey)) {
                    g = i -> i == null ? "N\\A" : String.valueOf(i);
                } else {
                    g = i -> i == null ? "N\\A" : "X";
                }

                for (Developer d : developers) {
                    AbstractItem abstractItem = new AbstractItem(
                            repeatingView.newChildId()
                    );
                    repeatingView.add(abstractItem);
                    AbstractLink editLink = getEditLink(
                            d,
                            view
                    );
                    abstractItem.add(editLink);

                    Label valueLabel = new Label(
                            "value",
                            g.getValue(view.getEstimations().get(d))
                    );
                    editLink.add(valueLabel);
                }
                item.add(repeatingView);
            }
        };

        add(new OrderBy(
                "orderByTaskName",
                "name",
                dp,
                () -> dataView.setCurrentPage(0)
        ));

        add(dataView);

        WebMarkupContainer wmc = new WebMarkupContainer("footer");
        wmc.add(new AttributeAppender("colspan", developers.size() + 1));
        wmc.add(new SemanticPagingNavigator("navigator", dataView));
        wmc.add(new SemanticNumEntriesPicker("numEntries", dataView));
        add(wmc);
    }

    private AbstractLink getEditLink(Developer d, EstimationRow row) {
        Estimation e = new Estimation();

        e.setDeveloper(d);
        e.setTask(row.getTask());
        e.setValue(row.getEstimations().getOrDefault(d, 0));

        return new Link("editLink") {
            @Override
            public void onClick() {
                EstimationEditPage page = new EstimationEditPage(e);
                setResponsePage(page);
            }
        };
    }

    private interface ValueGetter {
        String getValue(Integer i);
    }
}
