package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.db.DetachableEstimation;
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
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import javax.inject.Inject;
import java.util.*;
import java.util.function.BiFunction;
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

        final Comparator<Developer> devComp = Comparator
                .comparing(Developer::getSurname)
                .thenComparing(Developer::getGivenName)
                .thenComparing(Developer::getCreated);

        IModel<List<Developer>> developers = new LoadableDetachableModel<List<Developer>>() {
            @Override
            protected List<Developer> load() {
                return developerService
                        .getAllDevelopers()
                        .sorted(devComp)
                        .collect(Collectors.toList());
            }
        };

        RepeatingView headers = new RepeatingView("headerRow");

        for (Developer d : developers.getObject()) {
            headers.add(new Label(
                    headers.newChildId(),
                    d.getFullName()
            ));
        }

        add(headers);

        SortableEstimationRowDataProvider dp =
                new SortableEstimationRowDataProvider(taskService, developers);
        DataView<EstimationRow> dataView = new DataView<EstimationRow>("rows", dp) {
            @Override
            protected void populateItem(Item<EstimationRow> item) {
                EstimationRow view = item.getModelObject();

                item.add(new Label(
                        "taskName",
                        view.getTask().getName()
                ));

                final RepeatingView repeatingView = new RepeatingView("dataRow");

                final boolean allSet = view.allSet();

                BiFunction<String, Estimation, Label> iconLabelFun = (id, e) -> {
                    Label label = new Label(id);

                    if (allSet) {
                        Collection<String> classes = e == null ?
                                Arrays.asList("icon", "checkmark") :
                                Arrays.asList("icon", "help");

                        label.add(new ClassAttributeModifier() {
                            @Override
                            protected Set<String> update(Set<String> oldClasses) {
                                Set<String> ret = new HashSet<>(oldClasses);
                                ret.addAll(classes);
                                return ret;
                            }
                        });
                    }

                    return label;
                };
                final BiFunction<String, Estimation, Label> valueLabelFun = allSet ?
                        (id, e) -> new Label(id, "") :
                        (id, e) -> new Label(
                                id,
                                e == null ?
                                        "N\\A" :
                                        String.valueOf(e.getValue())
                        );

                view.getEstimations()
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey, devComp))
                    .forEachOrdered(e -> {
                        AbstractItem abstractItem = new AbstractItem(
                                repeatingView.newChildId()
                        );
                        repeatingView.add(abstractItem);
                        AbstractLink editLink = getEditLink(
                                e.getKey(),
                                view
                        );
                        abstractItem.add(editLink);

                        editLink.add(iconLabelFun.apply("icon", e.getValue()));
                        editLink.add(valueLabelFun.apply("value", e.getValue()));
                    });

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
        wmc.add(new AttributeAppender("colspan", developers.getObject().size() + 1));
        wmc.add(new SemanticPagingNavigator("navigator", dataView));
        wmc.add(new SemanticNumEntriesPicker("numEntries", dataView));
        add(wmc);
    }

    private AbstractLink getEditLink(IModel<Developer> d, IModel<EstimationRow> row) {
        Estimation e = row.getObject().getEstimations().get(d.getObject());
        WebPage page = e == null ? new EstimationAddPage() :
                new EstimationEditPage(new DetachableEstimation(
                        e.getId(),
                        estimationService
                )
                );

        return new Link("editLink") {
            @Override
            public void onClick() {
                setResponsePage(page);
            }
        };
    }

    private interface ValueGetter {
        String getValue(Integer i);
    }
}
