package nl.ramondevaan.taskestimation;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.service.EstimationService;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Profile("dev")
public class Seeder implements InitializingBean {
    @Autowired
    private DeveloperService  developerService;
    @Autowired
    private TaskService       taskService;
    @Autowired
    private EstimationService estimationService;

    public Seeder() {
        System.err.println("INITIALIZING SEEDER");
    }

    @Override
    public void afterPropertiesSet() {
        initializeDevelopers();
        initializeTasks();
        initializeEstimations();
    }

    private void initializeDevelopers() {
        System.err.println("INITIALIZING DEVELOPERS");
        List<Developer> developers = Arrays.asList(
                newDeveloper(
                        "Ramon",
                        "de",
                        "Vaan",
                        "ramon.de.vaan@capgemini.com"
                ),
                newDeveloper(
                        "Wilbert",
                        "de",
                        "Bever",
                        "wilbert.de.bever@genericemail.com"
                ),
                newDeveloper(
                        "Hans",
                        "",
                        "Peperkamp",
                        "hans.peperkamp@genericemail.com"
                ),
                newDeveloper(
                        "Annie",
                        "van",
                        "Wateren",
                        "a.van.wateren@genericemail.com"
                ),
                newDeveloper(
                        "Suzanne",
                        "",
                        "Schmidt",
                        "suzanne.schmidt@genericemail.com"
                )
        );

        developers.forEach(d -> developerService.addDeveloper(d));
    }

    private void initializeTasks() {
        System.err.println("INITIALIZING TASKS");
        List<Task> tasks = Arrays.asList(
                newTask(
                        "Water plants",
                        "Plants need water to survive!"
                ),
                newTask(
                        "Feed pets",
                        "Extra treats for Sparky"
                )
        );

        tasks.forEach(t -> taskService.addTask(t));
    }

    private void initializeEstimations() {
        System.err.println("INITIALIZING ESTIMATIONS");
        List<Developer> developers = developerService
                .getAllDevelopers()
                .collect(Collectors.toList());
        List<Task> tasks = taskService
                .getAllTasks()
                .collect(Collectors.toList());

        //First task is fully estimated
        if (tasks.size() <= 0) {
            System.err.println("Could not initialize estimations: no tasks");
            return;
        }
        if (developers.size() <= 0) {
            System.err.println("Could not initialize estimations: no developers");
            return;
        }

        Task                t0    = tasks.get(0);
        final AtomicInteger value = new AtomicInteger(1);
        for (Developer d : developers) {
            Estimation e = new Estimation();

            e.setTask(t0);
            e.setDeveloper(d);
            e.setValue(value.getAndIncrement());

            estimationService.addEstimation(e);
        }

        //Second task is partly estimated
        if (tasks.size() <= 1) {
            System.err.println(
                    "Could not fully initialize estimations: too little tasks"
            );
        }
        if (developers.size() <= 1) {
            System.err.println(
                    "Could not fully initialize estimations: too little developers"
            );
        }

        Task t1 = tasks.get(1);
        Collections.shuffle(developers);
        developers.stream().limit(developers.size() / 2).forEach(d -> {
            Estimation e = new Estimation();

            e.setTask(t1);
            e.setDeveloper(d);
            e.setValue(value.getAndIncrement());

            estimationService.addEstimation(e);
        });
    }

    private Developer newDeveloper(
            String givenName,
            String surnamePrefix,
            String surname,
            String email
    ) {
        Developer d = new Developer();

        d.setGivenName(givenName);
        d.setSurnamePrefix(surnamePrefix);
        d.setSurname(surname);
        d.setEmail(email);

        return d;
    }

    private Task newTask(String name, String description) {
        Task t = new Task();

        t.setName(name);
        t.setDescription(description);

        return t;
    }
}
