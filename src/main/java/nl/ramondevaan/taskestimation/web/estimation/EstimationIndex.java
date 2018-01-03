package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Developer;
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
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;

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
                RepeatingView repeatingView = new RepeatingView("dataRow");

                repeatingView.add(new Label(
                        repeatingView.newChildId(),
                        view.getTask().getName()
                ));

                ValueGetter g;
                if (developers.stream()
                              .allMatch(view.getEstimations()::containsKey)) {
                    g = i -> i == null ? "" : String.valueOf(i);
                } else {
                    g = i -> i == null ? "" : "X";
                }

                for (Developer d : developers) {
                    //TODO: Link to add/edit page in table
//                    if(view.getEstimations().containsKey(d)) {
//                        repeatingView.add(new BookmarkablePageLink<Void>(
//                                repeatingView.newChildId(),
//                                EstimationEdit.class,
//                        ))
//                    }

                    repeatingView.add(new Label(
                            repeatingView.newChildId(),
                            g.getValue(view.getEstimations().get(d))
                    ));
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

    private static interface ValueGetter {
        String getValue(Integer i);
    }
}
