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
        "estimations" }) public class Task {
    @Id @GeneratedValue private Long id;
    private LocalDateTime created;

    private String name;
    private String description;

    @OneToMany private List<Estimation> estimations;
}
