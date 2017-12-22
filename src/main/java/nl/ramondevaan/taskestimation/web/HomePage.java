package nl.ramondevaan.taskestimation.web;

import nl.ramondevaan.taskestimation.web.developer.DeveloperIndex;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
    public HomePage() {
        add(new DeveloperIndex("mainPanel"));
//        add(new DeveloperAdd("mainPanel"));
    }
}
