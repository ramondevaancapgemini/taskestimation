package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.web.BaseBorder;
import org.apache.wicket.markup.html.WebPage;

public class TaskIndexPage extends WebPage {
    public TaskIndexPage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
        border.add(new TaskIndex("taskIndexPanel"));
    }
}
