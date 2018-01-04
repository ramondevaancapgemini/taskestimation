package nl.ramondevaan.taskestimation.web.developer;

import org.apache.wicket.markup.html.panel.Panel;

public class DeveloperAdd extends Panel {

    public DeveloperAdd(String id) {
        super(id);
        DeveloperAddForm editForm = new DeveloperAddForm("developerEdit");
        add(editForm);
    }
}
