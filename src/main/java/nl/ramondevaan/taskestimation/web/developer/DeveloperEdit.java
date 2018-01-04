package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import org.apache.wicket.markup.html.panel.Panel;

public class DeveloperEdit extends Panel {
    public DeveloperEdit(String id, Developer developer) {
        super(id);
        DeveloperAddForm editForm = new DeveloperAddForm("developerEdit", developer);
        add(editForm);
    }
}
