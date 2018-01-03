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
    private Task        taskAdd;

    public TaskAddForm(String id) {
        super(id);

        taskAdd = new Task();
        setDefaultModel(new CompoundPropertyModel<>(taskAdd));

        add(new TextField<String>("name"));
        add(new TextArea<>("description"));
    }

    @Override
    protected void onSubmit() {
        System.out.println("USER PRESSED SUBMIT");
        System.out.println(taskAdd.toString());
        service.addTask(taskAdd);
    }
}
