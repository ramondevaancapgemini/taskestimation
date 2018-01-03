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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @OneToMany(orphanRemoval = true, mappedBy = "developer")
    private List<Estimation> estimations;

    public String getFullName() {
        return Stream
                .of(getGivenName(), getSurnamePrefix(), getSurname())
                .filter(Objects::nonNull)
                .collect(Collectors.joining("  "));
    }
}
