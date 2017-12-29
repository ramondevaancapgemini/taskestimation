package nl.ramondevaan.taskestimation.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = { "id" })
@ToString(exclude = { "estimations" })
public class Developer implements Serializable {
    public final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private Instant created;

    private String email;
    private String givenName;
    private String surnamePrefix;
    private String surname;

    @OneToMany(orphanRemoval = true)
    private List<Estimation> estimations;
}
