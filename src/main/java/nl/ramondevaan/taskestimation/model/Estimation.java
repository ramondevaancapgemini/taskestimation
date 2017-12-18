package nl.ramondevaan.taskestimation.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data @Entity public class Estimation {
    @Id @GeneratedValue private Long id;
    private LocalDateTime created;

    private int value;

    @ManyToOne private Developer developer;
    @ManyToOne private Task task;
}
