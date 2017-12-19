package nl.ramondevaan.taskestimation.model.view.developer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeveloperAdd {
    private String email;
    private String givenName;
    private String surnamePrefix;
    private String surname;
    private LocalDateTime birthDate;
}
