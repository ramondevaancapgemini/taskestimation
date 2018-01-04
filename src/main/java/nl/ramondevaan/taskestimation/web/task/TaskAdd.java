package nl.ramondevaan.taskestimation.web.task;

import org.apache.wicket.markup.html.panel.Panel;

public class TaskAdd extends Panel {

    public TaskAdd(String id) {
        super(id);
        TaskAddForm editForm = new TaskAddForm("taskEdit");
        add(editForm);
    }
}
