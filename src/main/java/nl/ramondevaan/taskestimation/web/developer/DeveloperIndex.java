package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.provider.SortableDeveloperDataProvider;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.utility.OrderBy;
import nl.ramondevaan.taskestimation.web.extension.SemanticNumEntriesPicker;
import nl.ramondevaan.taskestimation.web.extension.SemanticPagingNavigator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.stream.LongStream;

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
                repeatingView.add(new Label(
                        repeatingView.newChildId(),
                        view.getGivenName()
                ));
                repeatingView.add(new Label(
                        repeatingView.newChildId(),
                        view.getSurnamePrefix()
                ));
                repeatingView.add(new Label(
                        repeatingView.newChildId(),
                        view.getSurname()
                ));
                repeatingView.add(new Label(
                        repeatingView.newChildId(),
                        view.getEmail()
                ));
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
}
