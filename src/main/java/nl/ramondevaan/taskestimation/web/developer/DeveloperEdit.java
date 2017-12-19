package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;

public class DeveloperEdit extends WebPage {

    private TextField<String> emailField;
    private TextField<String> givenNameField;
    private TextField<String> surnamePrefixField;
    private TextField<String> surnameField;
    private DateTextField birthDateField;

    public DeveloperEdit() {

        Form<Developer> form = new Form<Developer>("form") {
            @Override protected void onSubmit() {
                info("Form.onSubmit executed");
            }
        };

        form.add(new TextField<String>("email"));
        form.add(new TextField<String>("givenname"));
        form.add(new TextField<String>("surnameprefix"));
        form.add(new TextField<String>("surname"));
        form.add(new NumberTextField<Integer>("age"));

        DatePicker datePicker = new DatePicker()
        {
            @Override
            protected String getAdditionalJavaScript()
            {
                return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
            }
        };
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(true);
        birthDateField.add(datePicker);

        add(form);
    }
}
