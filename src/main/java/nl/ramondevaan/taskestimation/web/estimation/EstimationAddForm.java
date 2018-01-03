package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.service.EstimationService;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;
import java.util.stream.Collectors;

public class EstimationAddForm extends Form {
    @Inject
    private EstimationService estimationService;
    @Inject
    private DeveloperService  developerService;
    @Inject
    private TaskService       taskService;

    private Estimation estimation;

    public EstimationAddForm(String id) {
        super(id);

        estimation = new Estimation();
        setDefaultModel(new CompoundPropertyModel<>(estimation));
        init();
    }

    public EstimationAddForm(String id, IModel model) {
        super(id, model);
        init();
    }

    private void init() {


        add(new DropDownChoice<>(
                "developer",
                developerService.getAllDevelopers().collect(Collectors.toList()),
                new ChoiceRenderer<Developer>() {
                    @Override
                    public Object getDisplayValue(Developer object) {
                        return object.getFullName();
                    }
                }
        ));
        add(new DropDownChoice<>(
                "task",
                taskService.getAllTasks().collect(Collectors.toList()),
                new ChoiceRenderer<Task>() {
                    @Override
                    public Object getDisplayValue(Task object) {
                        return object.getName();
                    }
                }
        ));
        add(new NumberTextField<Integer>("value"));
    }

    @Override
    protected void onSubmit() {
        System.out.println("USER PRESSED SUBMIT");
        System.out.println(estimation.toString());
        estimationService.addEstimation(estimation);
    }
}
