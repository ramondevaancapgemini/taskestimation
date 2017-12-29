package nl.ramondevaan.taskestimation.web.extension;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.AbstractPageableView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;

import java.util.Arrays;
import java.util.List;

public class SemanticNumEntriesPicker extends Panel {
    private final static List<Long> SIZES              = Arrays.asList(
            1L, 3L, 5L, 10L, 25L
    );
    private final static int        DEFAULT_SIZE_INDEX = 2;
    private final static long       DEFAULT_SIZE       = SIZES.get(DEFAULT_SIZE_INDEX);

    public SemanticNumEntriesPicker(String id, AbstractPageableView<?> dataView) {
        super(id);

        dataView.setItemsPerPage(DEFAULT_SIZE);
        RepeatingView repeatingView = new RepeatingView("numberOfEntries");
        SIZES.forEach(l -> {
            Link<Void> link = new Link<Void>(repeatingView.newChildId()) {
                @Override
                public void onClick() {
                    dataView.setItemsPerPage(l);
                }
            };
            link.add(new AttributeModifier(
                    "data-value",
                    l
            ));
            Label label = new Label("value", l);
            link.add(label);
            repeatingView.add(link);
        });
        add(repeatingView);

        HiddenField<Integer> numEntriesField = new HiddenField<>(
                "numberOfEntriesValue",
                new PropertyModel<>(
                        dataView,
                        "itemsPerPage"
                )
        );
        add(numEntriesField);
    }
}
