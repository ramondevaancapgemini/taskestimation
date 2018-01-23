package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class DeveloperEdit extends Panel {
    public DeveloperEdit(String id, IModel<Developer> developer) {
        super(id);
        DeveloperEditForm editForm = new DeveloperEditForm("developerEdit", developer);
        add(editForm);
    }
}
