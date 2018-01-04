package nl.ramondevaan.taskestimation.web.task;

import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;

public class TaskAddForm extends Form {
    @Inject
    private TaskService service;
    private Task        task;

    public TaskAddForm(String id) {
        this(id, new Task());
    }

    public TaskAddForm(String id, Task task) {
        super(id);

        this.task = task;
        setDefaultModel(new CompoundPropertyModel<>(this.task));

        add(new TextField<String>("name"));
        add(new TextArea<>("description"));
    }

    @Override
    protected void onSubmit() {
        service.addTask(this.task);
        setResponsePage(new TaskIndexPage());
    }
}
