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
    private Developer        developer;

    public DeveloperAddForm(String id) {
        this(id, new Developer());
    }

    public DeveloperAddForm(String id, Developer developer) {
        super(id);

        this.developer = developer;
        setDefaultModel(new CompoundPropertyModel<>(this.developer));

        add(new EmailTextField("email"));
        add(new TextField<String>("givenName"));
        add(new TextField<String>("surnamePrefix"));
        add(new TextField<String>("surname"));
    }

    @Override
    protected void onSubmit() {
        service.addDeveloper(developer);
        setResponsePage(new DeveloperIndexPage());
    }
}
