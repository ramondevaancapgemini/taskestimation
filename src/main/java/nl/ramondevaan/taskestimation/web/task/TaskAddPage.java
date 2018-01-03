package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class TaskAddPage extends WebPage {
    public TaskAddPage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new TaskAdd("taskAddPanel"));
    }
}
