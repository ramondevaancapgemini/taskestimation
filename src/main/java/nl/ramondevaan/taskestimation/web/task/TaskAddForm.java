package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class TaskAddForm extends Form {
    @Inject
    private TaskService   service;
    private Task          task;

    public TaskAddForm(String id) {
        this(id, new Task());
    }

    public TaskAddForm(String id, Task task) {
        super(id);

        this.task = task;
        setDefaultModel(new CompoundPropertyModel<>(this.task));

        TextField<String> name = new TextField<>("name");
        name.setRequired(true);
        name.add(StringValidator.minimumLength(1));
        add(name);
//        name.setModelValue(new String[]{getString("FeedbackMessage.CSS.fatal")});

        TextArea<String> description = new TextArea<>("description");
        description.setRequired(true);
        description.add(StringValidator.minimumLength(1));
        add(description);

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackErrors");
        add(feedbackPanel);
    }

    @Override
    protected void onSubmit() {
        service.addTask(this.task);
        setResponsePage(new TaskIndexPage());
    }
}
