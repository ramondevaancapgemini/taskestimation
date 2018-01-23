package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class EstimationEdit extends Panel {
    public EstimationEdit(String id, IModel<Estimation> e) {
        super(id);
        EstimationEditForm editForm = new EstimationEditForm("estimationEdit", e);
        add(editForm);
    }
}
