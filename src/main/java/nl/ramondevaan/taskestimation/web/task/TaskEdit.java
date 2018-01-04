package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import org.apache.wicket.markup.html.panel.Panel;

public class TaskEdit extends Panel {
    public TaskEdit(String id, Task task) {
        super(id);
        TaskAddForm editForm = new TaskAddForm("taskEdit", task);
        add(editForm);
    }
}
