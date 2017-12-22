package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.view.developer.DeveloperView;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperIndex extends Panel {
    @Inject
    private DeveloperService developerService;

    public DeveloperIndex(String id) {
        super(id);

        List<DeveloperView> developers = developerService.getAllDevelopers()
                .collect(Collectors.toList());

        ListDataProvider<DeveloperView> listDataProvider = new ListDataProvider<>(
                developers);
        DataView<DeveloperView> dataView = new DataView<DeveloperView>("rows",
                listDataProvider
        ) {
            @Override
            protected void populateItem(Item<DeveloperView> item
            ) {
                DeveloperView view          = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRow");
                repeatingView.add(new Label(repeatingView.newChildId(),
                        view.getGivenName()
                ));
                repeatingView.add(new Label(repeatingView.newChildId(),
                        view.getSurnamePrefix()
                ));
                repeatingView.add(new Label(repeatingView.newChildId(),
                        view.getSurname()
                ));
                repeatingView.add(new Label(repeatingView.newChildId(),
                        view.getEmail()
                ));
                item.add(repeatingView);
            }
        };

        add(dataView);
    }
}
