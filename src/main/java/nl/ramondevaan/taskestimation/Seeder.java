package nl.ramondevaan.taskestimation;

import nl.ramondevaan.taskestimation.model.view.developer.DeveloperEdit;
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
    private DeveloperService developerService;
    @Autowired
    private TaskService taskService;
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
        List<DeveloperEdit> developers =  Arrays.asList(new DeveloperEdit() {{
            setGivenName("Ramon");
            setSurnamePrefix("de");
            setSurname("Vaan");
            setEmail("ramon.de.vaan@capgemini.com");
        }}, new DeveloperEdit() {{
            setGivenName("Wilbert");
            setSurnamePrefix("de");
            setSurname("Bever");
            setEmail("wilbert.de.bever@genericemail.com");
        }}, new DeveloperEdit() {{
            setGivenName("Hans");
            setSurnamePrefix("");
            setSurname("Peperkamp");
            setEmail("hans.peperkamp@genericemail.com");
        }}, new DeveloperEdit() {{
            setGivenName("Annie");
            setSurnamePrefix("van");
            setSurname("Wateren");
            setEmail("a.van.wateren@genericemail.com");
        }}, new DeveloperEdit() {{
            setGivenName("Suzanne");
            setSurnamePrefix("");
            setSurname("Schmidt");
            setEmail("suzanna.schmidt@genericemail.com");
        }});

        developers.forEach(d -> developerService.addDeveloper(d));
    }
}
