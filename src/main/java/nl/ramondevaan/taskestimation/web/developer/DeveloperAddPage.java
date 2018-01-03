package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class DeveloperAddPage extends WebPage {
    public DeveloperAddPage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new DeveloperAdd("developerAddPanel"));
    }
}
