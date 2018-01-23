package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import javax.inject.Inject;

public class TaskEditForm extends Form<Task> {
    @Inject
    private TaskService service;

    public TaskEditForm(String id, IModel<Task> model) {
        super(id, new CompoundPropertyModel<>(model));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> name = new TextField<>("name");
        name.setRequired(true);
        name.add(StringValidator.minimumLength(1));
        add(name);

        TextArea<String> description = new TextArea<>("description");
        description.setRequired(true);
        description.add(StringValidator.minimumLength(1));
        add(description);

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackErrors");
        add(feedbackPanel);
    }

    @Override
    protected void onSubmit() {
        service.addTask(getModelObject());
        setResponsePage(new TaskIndexPage());
    }
}
