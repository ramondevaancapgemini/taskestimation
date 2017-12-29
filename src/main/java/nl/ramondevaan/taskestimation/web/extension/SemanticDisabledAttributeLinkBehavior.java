package nl.ramondevaan.taskestimation.web.extension;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

public class SemanticDisabledAttributeLinkBehavior extends Behavior {

    private static final long serialVersionUID = 1L;

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        if (!component.isEnabledInHierarchy()) {
            // if the tag is an anchor proper
            String tagName = tag.getName();

            if (tagName.equalsIgnoreCase("a") || tagName.equalsIgnoreCase("link") ||
                    tagName.equalsIgnoreCase("area")) {
                tag.append("class", "disabled", " ");
            } else {
                tag.remove("rel");
            }
        }
    }
}
