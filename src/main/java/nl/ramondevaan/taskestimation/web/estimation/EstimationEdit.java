package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

public class EstimationEdit extends Panel {
    private EstimationAddForm editForm;

    public EstimationEdit(String id, Estimation e) {
        super(id);
        editForm = new EstimationAddForm("estimationEdit", e);
        add(editForm);
    }
}
