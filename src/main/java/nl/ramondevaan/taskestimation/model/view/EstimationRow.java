package nl.ramondevaan.taskestimation.model.view;

import lombok.Data;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class EstimationRow implements Serializable {
    private final Task                    task;
    private final Map<Developer, Integer> estimations;

    public final static EstimationRow create(Task t) {
        Map<Developer, List<Estimation>> map = t
                .getEstimations()
                .stream()
                .collect(Collectors.toMap(
                        Estimation::getDeveloper,
                        Collections::singletonList,
                        (l1, l2) -> {
                            List<Estimation> ret = new ArrayList<>();
                            ret.addAll(l1);
                            ret.addAll(l2);
                            return ret;
                        }
                ));
        map.values().forEach(v -> v.sort(Comparator.comparing(
                Estimation::getCreated, (Comparator<Instant>) Instant::compareTo)));

        Map<Developer, Integer> lastEstimation = map
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        f -> f.getValue().get(f.getValue().size() - 1).getValue()
                ));

        return new EstimationRow(t, lastEstimation);
    }
}
