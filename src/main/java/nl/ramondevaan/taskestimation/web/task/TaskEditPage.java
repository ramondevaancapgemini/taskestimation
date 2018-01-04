package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class TaskEditPage extends WebPage {
    public TaskEditPage() {

    }

    public TaskEditPage(Task task) {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new TaskEdit("taskEditPanel", task));
    }
}
