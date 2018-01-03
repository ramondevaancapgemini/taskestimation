package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Estimation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class EstimationEdit extends Panel {
    private EstimationAddForm editForm;

    public EstimationEdit(String id, IModel<Estimation> model) {
        super(id, model);
        editForm = new EstimationAddForm("estimationEdit", model);
        add(editForm);
    }
}
