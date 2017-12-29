package nl.ramondevaan.taskestimation.web.extension;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.navigation.paging.IPageable;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SemanticActiveAttributeLinkBehavior extends Behavior {
    private static final long serialVersionUID = 1L;
    private final IPageable pageable;
    private final long      page;

    public SemanticActiveAttributeLinkBehavior(IPageable pageable, long page) {
        this.pageable = pageable;
        this.page = page;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        Stream<String> classes = Arrays
                .stream(tag.getAttribute("class").split(" "));


        if (page == pageable.getCurrentPage()) {
            classes = Stream.concat(classes, Stream.of("active"));
        } else {
            classes = classes.filter(s -> !s.equals("active"));
        }

        tag.put("class", classes.collect(Collectors.joining(" ")));
    }
}
