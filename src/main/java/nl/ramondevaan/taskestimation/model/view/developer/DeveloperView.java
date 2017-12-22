package nl.ramondevaan.taskestimation.model.view.developer;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeveloperView implements Serializable {
    public final static long serialVersionUID = 1L;

    private String email;
    private String givenName;
    private String surnamePrefix;
    private String surname;
}
