package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;

public class DeveloperAddForm extends Form {
    @Inject
    private DeveloperService service;
    private Developer        developerAdd;

    public DeveloperAddForm(String id) {
        super(id);

        developerAdd = new Developer();
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
        service.addDeveloper(developerAdd);
    }
}
