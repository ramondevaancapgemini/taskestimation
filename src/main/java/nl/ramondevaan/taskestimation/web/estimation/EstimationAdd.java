package nl.ramondevaan.taskestimation.web.estimation;

import org.apache.wicket.markup.html.panel.Panel;

public class EstimationAdd extends Panel {

    private EstimationAddForm editForm;

    public EstimationAdd(String id) {
        super(id);
        editForm = new EstimationAddForm("estimationEdit");
        add(editForm);
    }
}
