package nl.ramondevaan.taskestimation.model.view;

import lombok.Data;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EstimationRow {
    private final Task task;
    private final List<Estimation> estimations;

    public boolean allSet() {
        return estimations.stream().allMatch(e -> e.getValue() > 0);
    }

    public static EstimationRow create(Task task, List<Developer> developers) {
        List<Estimation> list = developers.stream()
                .map(d -> task.getEstimations().stream()
                        .filter(e -> e.getDeveloper().equals(d)).max(Comparator
                                .comparing(Estimation::getCreated,
                                        Instant::compareTo)).orElseGet(() -> {
                            Estimation est = new Estimation();

                            est.setValue(0);
                            est.setTask(task);
                            est.setDeveloper(d);

                            return est;
                        })).collect(Collectors.toList());

        return new EstimationRow(task, Collections.unmodifiableList(list));
    }
}
