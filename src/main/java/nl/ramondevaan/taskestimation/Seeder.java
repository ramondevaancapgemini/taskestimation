package nl.ramondevaan.taskestimation;

import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.service.DeveloperService;
import nl.ramondevaan.taskestimation.service.EstimationService;
import nl.ramondevaan.taskestimation.service.TaskService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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
    public void afterPropertiesSet() throws Exception {
        System.err.println("INITIALIZING DEVELOPERS");
        initializeDevelopers();
    }

    private void initializeDevelopers() {
        List<Developer> developers = Arrays.asList(
                newDeveloper("Ramon", "de", "Vaan", "ramon.de.vaan@capgemini.com"),
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
}
