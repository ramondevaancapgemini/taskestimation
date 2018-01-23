package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class TaskEdit extends Panel {
    public TaskEdit(String id, IModel<Task> task) {
        super(id);
        TaskEditForm editForm = new TaskEditForm("taskEdit", task);
        add(editForm);
    }
}
