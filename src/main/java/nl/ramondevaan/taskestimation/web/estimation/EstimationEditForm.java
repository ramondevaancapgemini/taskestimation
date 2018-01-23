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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.validator.RangeValidator;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class EstimationEditForm extends Form<Estimation> {
    @Inject
    private EstimationService estimationService;
    @Inject
    private DeveloperService developerService;
    @Inject
    private TaskService taskService;

    public EstimationEditForm(String id, IModel<Estimation> model) {
        super(id, new CompoundPropertyModel<>(model));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DropDownChoice<Developer> developer = new DropDownChoice<>(
                "developer",
                new LoadableDetachableModel<List<Developer>>() {
                    @Override
                    protected List<Developer> load() {
                        return developerService
                                .getAllDevelopers()
                                .collect(Collectors.toList());
                    }
                },
                new ChoiceRenderer<Developer>() {
                    @Override
                    public Object getDisplayValue(Developer object) {
                        return object.getFullName();
                    }
                }
        );
        developer.setRequired(true);
        add(developer);

        DropDownChoice<Task> task = new DropDownChoice<>(
                "task",
                new LoadableDetachableModel<List<Task>>() {
                    @Override
                    protected List<Task> load() {
                        return taskService
                                .getAllTasks()
                                .collect(Collectors.toList());
                    }
                },
                new ChoiceRenderer<Task>() {
                    @Override
                    public Object getDisplayValue(Task object) {
                        return object.getName();
                    }
                }
        );
        task.setRequired(true);
        add(task);

        RangeValidator<Integer> rangeValidator = RangeValidator.minimum(1);
        NumberTextField<Integer> value = new NumberTextField<>("value");
        value.add(rangeValidator);
        value.setRequired(true);
        add(value);

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackErrors");
        add(feedbackPanel);
    }

    @Override
    protected void onSubmit() {
        estimationService.addEstimation(getModelObject());
        setResponsePage(new EstimationIndexPage());
    }
}
