package nl.ramondevaan.taskestimation.web;

import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
    public HomePage() {
        BaseBorder border = new BaseBorder("border");
        add(border);
    }
}
