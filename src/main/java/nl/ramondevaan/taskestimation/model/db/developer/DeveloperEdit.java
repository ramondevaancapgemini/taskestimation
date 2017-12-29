package nl.ramondevaan.taskestimation.model.db.developer;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeveloperEdit implements Serializable {
    public final static long serialVersionUID = 1L;

    private String email;
    private String givenName;
    private String surnamePrefix;
    private String surname;
}
