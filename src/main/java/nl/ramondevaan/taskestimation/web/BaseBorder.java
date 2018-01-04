package nl.ramondevaan.taskestimation.web;

import nl.ramondevaan.taskestimation.web.developer.DeveloperAddPage;
import nl.ramondevaan.taskestimation.web.developer.DeveloperIndexPage;
import nl.ramondevaan.taskestimation.web.estimation.EstimationAddPage;
import nl.ramondevaan.taskestimation.web.estimation.EstimationIndexPage;
import nl.ramondevaan.taskestimation.web.extension.SemanticActiveAttributeLinkBehavior;
import nl.ramondevaan.taskestimation.web.task.TaskAddPage;
import nl.ramondevaan.taskestimation.web.task.TaskIndexPage;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class BaseBorder extends Border {
    public BaseBorder(String id) {
        super(id);

        addLink("homePage", HomePage.class);
        addLink("developerIndexPage", DeveloperIndexPage.class);
        addLink("developerAddPage", DeveloperAddPage.class);
        addLink("taskIndexPage", TaskIndexPage.class);
        addLink("taskAddPage", TaskAddPage.class);
        addLink("estimationIndexPage", EstimationIndexPage.class);
        addLink("estimationAddPage", EstimationAddPage.class);
    }

    private void addLink(String id, Class<? extends Page> clazz) {
        BookmarkablePageLink<Void> link = new BookmarkablePageLink<>(id, clazz);
        link.setAutoEnable(true);
        link.add(new SemanticActiveAttributeLinkBehavior());
        addToBorder(link);
    }
}
