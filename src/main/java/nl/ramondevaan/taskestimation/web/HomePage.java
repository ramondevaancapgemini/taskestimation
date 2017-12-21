package nl.ramondevaan.taskestimation.web;

import nl.ramondevaan.taskestimation.web.developer.DeveloperAdd;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends WebPage {
    public HomePage() {
        add(new DeveloperAdd("addDeveloper"));
    }
}
