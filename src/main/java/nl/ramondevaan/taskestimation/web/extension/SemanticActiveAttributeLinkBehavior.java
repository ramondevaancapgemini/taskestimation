package nl.ramondevaan.taskestimation.web.extension;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SemanticActiveAttributeLinkBehavior extends Behavior {
    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        String classAttr = tag.getAttribute("class");
        if(classAttr == null) {
            classAttr = "";
        }

        Stream<String> classes = Arrays.stream(classAttr.split("\\s+"));

        if (tag.getAttribute("href") == null) {
            classes = Stream.concat(classes, Stream.of("active"));
        } else {
            classes = classes.filter(s -> !s.equals("active"));
        }

        tag.put("class", classes.collect(Collectors.joining(" ")));
    }
}
