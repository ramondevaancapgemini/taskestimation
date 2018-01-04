package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class DeveloperEditPage extends WebPage {
    public DeveloperEditPage() {
    }

    public DeveloperEditPage(Developer developer) {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new DeveloperEdit("developerEditPanel", developer));
    }
}
