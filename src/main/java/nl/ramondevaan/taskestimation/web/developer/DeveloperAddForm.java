package nl.ramondevaan.taskestimation.web.developer;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

import javax.inject.Inject;

public class DeveloperAddForm extends Form {
    @Inject
    private DeveloperService service;

    private String email;
    private String givenName;
    private String surnamePrefix;
    private String surname;

    public DeveloperAddForm(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setDefaultModel(new CompoundPropertyModel<>(this));

        EmailTextField email = new EmailTextField("email");
        email.add(EmailAddressValidator.getInstance());
        email.setRequired(true);
        add(email);

        TextField<String> givenName = new TextField<>("givenName");
        givenName.add(StringValidator.minimumLength(2));
        givenName.setRequired(true);
        add(givenName);

        TextField<String> surnamePrefix = new TextField<>("surnamePrefix");
        surnamePrefix.setRequired(false);
        surnamePrefix.add(StringValidator.minimumLength(1));
        add(surnamePrefix);

        TextField<String> surname = new TextField<>("surname");
        surname.setRequired(true);
        surname.add(StringValidator.minimumLength(2));
        add(surname);

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackErrors");
        add(feedbackPanel);
    }

    @Override
    protected void onSubmit() {
        Developer d = new Developer();
        d.setEmail(email);
        d.setGivenName(givenName);
        d.setSurnamePrefix(surnamePrefix);
        d.setSurname(surname);

        service.addDeveloper(d);
        setResponsePage(new DeveloperIndexPage());
    }
}
