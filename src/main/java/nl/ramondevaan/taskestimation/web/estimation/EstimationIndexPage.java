package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class EstimationIndexPage extends WebPage {
    public EstimationIndexPage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new EstimationIndex("estimationIndexPanel"));
    }
}
