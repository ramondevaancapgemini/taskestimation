package nl.ramondevaan.taskestimation.web.estimation;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.service.EstimationService;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.Collections;
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
        this(id, new Estimation());
    }

    public EstimationAddForm(String id, Estimation estimation) {
        super(id);

        this.estimation = estimation;

        setDefaultModel(new CompoundPropertyModel<>(estimation));

        add(new DropDownChoice<>(
                "developer",
                estimation.getDeveloper() != null ?
                        Collections.singletonList(estimation.getDeveloper()) :
                        developerService.getAllDevelopers()
                                        .collect(Collectors.toList()),
                new ChoiceRenderer<Developer>() {
                    @Override
                    public Object getDisplayValue(Developer object) {
                        return object.getFullName();
                    }
                }
        ));
        add(new DropDownChoice<>(
                "task",
                estimation.getTask() != null ?
                        Collections.singletonList(estimation.getTask()) :
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
        estimationService.addEstimation(estimation);
        setResponsePage(new EstimationIndexPage());
    }
}
