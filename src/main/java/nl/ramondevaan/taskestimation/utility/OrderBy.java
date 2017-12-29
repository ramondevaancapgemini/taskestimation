package nl.ramondevaan.taskestimation.utility;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.ComponentTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderBy extends OrderByBorder<String> {
    private final transient SortListener sortListener;

    public OrderBy(
            String id,
            String property,
            ISortStateLocator<String> stateLocator
    ) {
        this(id, property, stateLocator, null);
    }

    public OrderBy(
            String id,
            String property,
            ISortStateLocator<String> stateLocator,
            SortListener sortListener
    ) {
        super(id, property, stateLocator);
        this.sortListener = sortListener == null ?
                () -> {
                } :
                sortListener;
    }

    @Override
    protected final void onSortChanged() {
        onSortChangedExt();
        sortListener.onSortChanged();
    }

    protected void onSortChangedExt() {

    }

    @Override
    public void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        List<String> classes = Arrays.asList(tag.getAttribute("class").split(" "));

        List<String> toAppend = new ArrayList<>();

        toAppend.add("sorted");
        if (classes.contains(getString(SORT_ASCENDING_CSS_CLASS_KEY))) {
            toAppend.add("ascending");
        } else if (classes.contains(getString(SORT_DESCENDING_CSS_CLASS_KEY))) {
            toAppend.add("descending");
        }

        toAppend.forEach(s -> tag.append("class", s, " "));
    }
}
