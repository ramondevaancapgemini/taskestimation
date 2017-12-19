package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.Developer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;

public class DeveloperAdd extends WebPage {
    public DeveloperAdd() {

        Form<Developer> form = new Form<Developer>("form") {
            @Override protected void onSubmit() {
                info("Form.onSubmit executed");
            }
        };

        form.add(new TextField<String>("givenname"));
        form.add(new TextField<String>("surnameprefix"));
        form.add(new TextField<String>("surname"));
        form.add(new NumberTextField<Integer>("age"));

        add(form);
    }
}
