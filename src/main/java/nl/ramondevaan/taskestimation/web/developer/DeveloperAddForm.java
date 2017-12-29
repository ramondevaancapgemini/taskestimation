package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.db.developer.DeveloperEdit;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class DeveloperAddForm extends Form {
    private DeveloperEdit developerAdd;

    public DeveloperAddForm(String id) {
        super(id);

        developerAdd = new DeveloperEdit();
        setDefaultModel(new CompoundPropertyModel<>(developerAdd));

        add(new EmailTextField("email"));
        add(new TextField<String>("givenName"));
        add(new TextField<String>("surnamePrefix"));
        add(new TextField<String>("surname"));
    }

    @Override
    protected void onSubmit() {
        System.out.println("USER PRESSED SUBMIT");
        System.out.println(developerAdd.toString());
    }
}
