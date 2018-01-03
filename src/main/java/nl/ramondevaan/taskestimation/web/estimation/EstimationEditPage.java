package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class EstimationEditPage extends WebPage {
    public EstimationEditPage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
        //TODO: Supply model
//        border.add(new EstimationEdit("estimationEditPanel"));
    }
}
