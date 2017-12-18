package nl.ramondevaan.taskestimation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TaskEstimationApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(TaskEstimationApplication.class)
                .run(args);
    }
}
