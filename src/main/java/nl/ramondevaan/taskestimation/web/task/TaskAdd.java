package nl.ramondevaan.taskestimation.web.task;

import org.apache.wicket.markup.html.panel.Panel;

public class TaskAdd extends Panel {

    private TaskAddForm editForm;

    public TaskAdd(String id) {
        super(id);
        editForm = new TaskAddForm("taskEdit");
        add(editForm);
    }
}
