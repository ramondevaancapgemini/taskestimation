package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class EstimationEditPage extends WebPage {
    public EstimationEditPage() {
    }

    public EstimationEditPage(Estimation estimation) {
        BaseBorder border = new BaseBorder("border");
        add(border);
        EstimationEdit edit = new EstimationEdit(
                "estimationEditPanel",
                estimation
        );
        border.add(edit);
    }
}
