package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.provider.SortableDeveloperDataProvider;
import nl.ramondevaan.taskestimation.service.DeveloperService;
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

import javax.inject.Inject;

public class DeveloperIndex extends Panel {
    @Inject
    private DeveloperService service;

    public DeveloperIndex(String id) {
        super(id);

        SortableDeveloperDataProvider dp = new SortableDeveloperDataProvider(service);
        DataView<Developer> dataView = new DataView<Developer>("rows", dp) {
            @Override
            protected void populateItem(Item<Developer> item) {
                Developer     view          = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRow");

                repeatingView.add(getCell(
                        repeatingView.newChildId(),
                        view,
                        view.getGivenName()
                ));
                repeatingView.add(getCell(
                        repeatingView.newChildId(),
                        view,
                        view.getSurnamePrefix()
                ));
                repeatingView.add(getCell(
                        repeatingView.newChildId(),
                        view,
                        view.getSurname()
                ));
                repeatingView.add(getCell(
                        repeatingView.newChildId(),
                        view,
                        view.getEmail()
                ));

                Link deleteLink = new Link("deleteAction") {
                    @Override
                    public void onClick() {
                        service.removeDeveloper(view.getId());
                    }
                };
                item.add(deleteLink);

                item.add(repeatingView);
            }
        };

        add(new OrderBy(
                "orderByGivenName",
                "givenName",
                dp,
                () -> dataView.setCurrentPage(0)
        ));
        add(new OrderBy(
                "orderBySurnamePrefix",
                "surnamePrefix",
                dp,
                () -> dataView.setCurrentPage(0)
        ));
        add(new OrderBy(
                "orderBySurname",
                "surname",
                dp,
                () -> dataView.setCurrentPage(0)
        ));
        add(new OrderBy(
                "orderByEmail",
                "email",
                dp,
                () -> dataView.setCurrentPage(0)
        ));

        add(dataView);

        add(new SemanticPagingNavigator("navigator", dataView));
        add(new SemanticNumEntriesPicker("numEntries", dataView));
    }

    private AbstractItem getCell(String id, Developer d, String value) {
        AbstractItem item  = new AbstractItem(id);
        Label        label = new Label("cellValue", value);
        Link link = new Link("cellLink") {
            @Override
            public void onClick() {
                setResponsePage(new DeveloperEditPage(d));
            }
        };

        item.add(link);
        link.add(label);

        return item;
    }
}
