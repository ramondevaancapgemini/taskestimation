package nl.ramondevaan.taskestimation.model.view;

import lombok.Data;
import nl.ramondevaan.taskestimation.model.domain.Developer;
import nl.ramondevaan.taskestimation.model.domain.Estimation;
import nl.ramondevaan.taskestimation.model.domain.Task;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class EstimationRow {
    private final Task                       task;
    private final Map<Developer, Estimation> estimations;

//    public final static EstimationRow create(Task t) {
//        Map<Developer, List<Estimation>> map = t
//                .getEstimations()
//                .stream()
//                .collect(Collectors.toMap(
//                        Estimation::getDeveloper,
//                        Collections::singletonList,
//                        (l1, l2) -> {
//                            List<Estimation> ret = new ArrayList<>();
//                            ret.addAll(l1);
//                            ret.addAll(l2);
//                            return ret;
//                        }
//                ));
//        map.values().forEach(v -> v.sort(Comparator.comparing(
//                Estimation::getCreated, (Comparator<Instant>) Instant::compareTo)));
//
//        Map<Developer, Estimation> lastEstimation = map
//                .entrySet()
//                .stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        f -> f.getValue().get(f.getValue().size() - 1)
//                ));
//
//        return new EstimationRow(t, lastEstimation);
//    }

    public boolean allSet() {
        return estimations.values().stream().allMatch(Objects::nonNull);
    }

    public final static EstimationRow create(Task task, List<Developer> developers) {
        Map<Developer, Estimation> map = developers
                .stream()
                .map(d -> ImmutablePair.of(
                        d,
                        task.getEstimations()
                            .stream()
                            .filter(e -> e.getDeveloper().equals(d))
                            .max(Comparator.comparing(
                                    Estimation::getCreated,
                                    Instant::compareTo
                            ))
                            .orElse(null)
                ))
                .collect(Collectors.toMap(
                        ImmutablePair::getLeft,
                        ImmutablePair::getRight
                ));

        return new EstimationRow(task, Collections.unmodifiableMap(map));
    }
}
