package nl.ramondevaan.taskestimation.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data @Entity public class Developer {
    @Id @GeneratedValue private Long id;
    private LocalDateTime created;

    private String givenName;
    private String surnamePrefix;
    private String surname;
    private int age;

    @OneToMany(orphanRemoval = true) private List<Estimation> estimations;
}
