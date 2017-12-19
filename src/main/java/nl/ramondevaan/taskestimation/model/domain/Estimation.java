package nl.ramondevaan.taskestimation.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data @Entity @EqualsAndHashCode(of = { "id" }) public class Estimation {
    @Id @GeneratedValue private Long id;
    private LocalDateTime created;

    private int value;

    @ManyToOne private Developer developer;
    @ManyToOne private Task task;
}
