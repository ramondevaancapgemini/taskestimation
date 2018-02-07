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
import java.util.function.Predicate;
import java.util.function.Supplier;
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

        IModel<List<Developer>> developers =
                new LoadableDetachableModel<List<Developer>>() {
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

        SortableEstimationRowDataProvider dp =
                new SortableEstimationRowDataProvider(
                        taskService,
                        developers
                );
        DataView<EstimationRow> dataView = new DataView<EstimationRow>(
                "rows",
                dp
        ) {
            @Override
            protected void populateItem(Item<EstimationRow> item) {
                item.add(new Label("taskName",
                        new PropertyModel<>(
                                item.getModel(),
                                "task.name"
                        )
                ));

                ListView<Estimation> estimations = new ListView<Estimation>(
                        "dataRow",
                        new PropertyModel<>(item.getModel(),
                                "estimations")
                ) {
                    @Override
                    protected void populateItem(
                            ListItem<Estimation> innerItem
                    ) {
                        ItemState is = new ItemState();
                        is.rowItem = item;
                        is.estimationItem = innerItem;

                        Link<Estimation> link = new Link<Estimation>(
                                "editLink",
                                innerItem.getModel()
                        ) {
                            @Override
                            public void onClick() {
                                EstimationIndex.this.setResponsePage(
                                        new EstimationEditPage(getModel())
                                );
                            }
                        };
                        innerItem.add(link);

                        Label value = new Label(
                                "value",
                                item.getModelObject().allSet() ?
                                        innerItem.getModelObject().getValue() :
                                        ""
                        );
                        link.add(value);

                        Collection<String> cellClasses = getCellClassMap(is);
                        Collection<String> iconClasses = getIconClasses(is);

                        innerItem.add(new ClassAppender(cellClasses));
                        Label icon = new Label("icon", "");
                        icon.add(new ClassAppender(iconClasses));
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

    private Collection<String> getIconClasses(ItemState itemState) {
        Map<Predicate<ItemState>, ClassSupplier> ret = new HashMap<>();

        ret.put(
                is -> !is.rowItem.getModelObject().allSet() &&
                        is.estimationItem.getModelObject().getValue() > 0,
                () -> Arrays.asList("icon", "checkmark")
        );
        ret.put(
                is -> !is.rowItem.getModelObject().allSet() &&
                        is.estimationItem.getModelObject().getValue() <= 0,
                () -> Arrays.asList("icon", "help")
        );
        ret.put(
                is -> is.rowItem.getModelObject().allSet() &&
                        !is.rowItem.getModelObject().allEstimationsEqual() &&
                        Objects.equals(
                                is.estimationItem.getModelObject().getValue(),
                                is.rowItem.getModelObject().largestEstimation()
                        ),
                () -> Arrays.asList("icon", "angle", "double", "up")
        );
        ret.put(
                is -> is.rowItem.getModelObject().allSet() &&
                        !is.rowItem.getModelObject().allEstimationsEqual() &&
                        Objects.equals(
                                is.estimationItem.getModelObject().getValue(),
                                is.rowItem.getModelObject().smallestEstimation()
                        ),
                () -> Arrays.asList("icon", "angle", "double", "down")
        );

        return ret
                .entrySet()
                .stream()
                .filter(cc -> cc.getKey().test(itemState))
                .map(cc -> cc.getValue().get())
                .findFirst()
                .orElseGet(Collections::emptySet);
    }

    private Collection<String> getCellClassMap(ItemState itemState) {
        Map<Predicate<ItemState>, ClassSupplier> ret = new HashMap<>();

        ret.put(
                is -> is.rowItem.getModelObject().allSet() &&
                        !is.rowItem.getModelObject().allEstimationsEqual() &&
                        Objects.equals(
                                is.estimationItem.getModelObject().getValue(),
                                is.rowItem.getModelObject().largestEstimation()
                        ),
                () -> Collections.singletonList("positive")
        );
        ret.put(
                is -> is.rowItem.getModelObject().allSet() &&
                        !is.rowItem.getModelObject().allEstimationsEqual() &&
                        Objects.equals(
                                is.estimationItem.getModelObject().getValue(),
                                is.rowItem.getModelObject().smallestEstimation()
                        ),
                () -> Collections.singletonList("negative")
        );

        return ret
                .entrySet()
                .stream()
                .filter(cc -> cc.getKey().test(itemState))
                .map(cc -> cc.getValue().get())
                .findFirst()
                .orElseGet(Collections::emptySet);
    }

    private class ItemState {
        private Item<EstimationRow> rowItem;
        private ListItem<Estimation> estimationItem;
    }

    private interface ClassSupplier extends Supplier<Collection<String>> {

    }

    private class ClassAppender extends AttributeAppender {

        private ClassAppender(Collection<String> classes) {
            super(
                    "class",
                    classes.stream()
                           .collect(Collectors.joining(" ")),
                    " "
            );
        }
    }
}
