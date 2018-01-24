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
import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class EstimationIndex extends Panel {
    @Inject
    private DeveloperService developerService;
    @Inject
    private TaskService taskService;
    @Inject
    private EstimationService estimationService;

    public EstimationIndex(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel<List<Developer>> developers = new LoadableDetachableModel<List<Developer>>() {
            @Override
            protected List<Developer> load() {
                return developerService.getAllDevelopers().sorted(Comparator
                        .comparing(Developer::getSurname)
                        .thenComparing(Developer::getGivenName)
                        .thenComparing(Developer::getCreated))
                        .collect(Collectors.toList());
            }
        };

        RepeatingView headers = new RepeatingView("headerRow");

        for (Developer d : developers.getObject()) {
            headers.add(new Label(headers.newChildId(), d.getFullName()));
        }

        add(headers);

        SortableEstimationRowDataProvider dp = new SortableEstimationRowDataProvider(
                taskService, developers);
        DataView<EstimationRow> dataView = new DataView<EstimationRow>("rows",
                dp) {
            @Override
            protected void populateItem(Item<EstimationRow> item) {
                EstimationRow view = item.getModelObject();

                item.add(new Label("taskName",
                        new PropertyModel<>(item.getModel(), "task.name")));

                final boolean allSet = view.allSet();

                ListView<Estimation> estimations = new ListView<Estimation>("dataRow", new PropertyModel<>(item.getModel(), "estimations")) {
                    @Override
                    protected void populateItem(ListItem<Estimation> item) {
                        Link<Estimation> link = new Link<Estimation>("editLink", item.getModel()) {
                            @Override
                            public void onClick() {
                                EstimationIndex.this.setResponsePage(new EstimationEditPage(getModel()));
                            }
                        };
                        item.add(link);

                        Label value = new Label("value", allSet ?
                                item.getModelObject().getValue() : "");
                        link.add(value);

                        Label icon = new Label("icon", "");
                        if(!allSet) {
                            Collection<String> classes = item.getModelObject().getValue() > 0 ?
                                    Arrays.asList("icon", "checkmark") :
                                    Arrays.asList("icon", "help");

                            icon.add(new ClassAttributeModifier() {
                                @Override
                                protected Set<String> update(Set<String> oldClasses) {
                                    Set<String> ret = new HashSet<>(oldClasses);
                                    ret.addAll(classes);
                                    return ret;
                                }
                            });
                        }
                        link.add(icon);
                    }
                };

                item.add(estimations);
            }
        };

        add(new OrderBy("orderByTaskName", "name", dp,
                () -> dataView.setCurrentPage(0)));

        add(dataView);

        WebMarkupContainer wmc = new WebMarkupContainer("footer");
        wmc.add(new AttributeAppender("colspan",
                developers.getObject().size() + 1));
        wmc.add(new SemanticPagingNavigator("navigator", dataView));
        wmc.add(new SemanticNumEntriesPicker("numEntries", dataView));
        add(wmc);
    }
}
