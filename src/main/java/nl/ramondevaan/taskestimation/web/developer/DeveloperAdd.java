package nl.ramondevaan.taskestimation.web.developer;

import org.apache.wicket.markup.html.panel.Panel;

public class DeveloperAdd extends Panel {

    private DeveloperAddForm editForm;

    public DeveloperAdd(String id) {
        super(id);
        editForm = new DeveloperAddForm("developerEdit");
        add(editForm);
    }
}
