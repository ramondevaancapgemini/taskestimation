package nl.ramondevaan.taskestimation.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"estimations"})
public class Task {
    @Id
    @GeneratedValue
    private Long    id;
    private Instant created;

    private String name;
    private String description;

    @OneToMany(
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "task"
    )
    private List<Estimation> estimations;
}
