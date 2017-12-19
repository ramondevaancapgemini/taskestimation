package nl.ramondevaan.taskestimation.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data @Entity @EqualsAndHashCode(of = { "id" }) @ToString(exclude = {
        "estimations" }) public class Developer {
    @Id @GeneratedValue private Long id;
    private LocalDateTime created;

    private String email;
    private String givenName;
    private String surnamePrefix;
    private String surname;
    private LocalDateTime birthDate;

    @OneToMany(orphanRemoval = true) private List<Estimation> estimations;
}
