package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class DeveloperIndexPage extends WebPage {
    public DeveloperIndexPage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new DeveloperIndex("developerIndexPanel"));
    }
}
