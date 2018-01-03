package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class EstimationAddPage extends WebPage {
    public EstimationAddPage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new EstimationAdd("estimationAddPanel"));
    }
}
