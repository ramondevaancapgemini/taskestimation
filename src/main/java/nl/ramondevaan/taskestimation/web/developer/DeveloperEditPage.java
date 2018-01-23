package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

public class DeveloperEditPage extends WebPage {
    public DeveloperEditPage() {
    }

    public DeveloperEditPage(IModel<Developer> developer) {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new DeveloperEdit("developerEditPanel", developer));
    }
}
