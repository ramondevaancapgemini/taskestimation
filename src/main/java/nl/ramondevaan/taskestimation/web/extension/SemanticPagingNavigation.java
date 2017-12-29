package nl.ramondevaan.taskestimation.web.extension;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.model.Model;

public class SemanticPagingNavigation extends PagingNavigation {
    public SemanticPagingNavigation(String id, IPageable pageable) {
        super(id, pageable);
    }

    public SemanticPagingNavigation(
            String id, IPageable pageable,
            IPagingLabelProvider labelProvider
    ) {
        super(id, pageable, labelProvider);
    }

    @Override
    protected AbstractLink newPagingNavigationLink(
            String id, IPageable pageable, long pageIndex
    ) {
        AbstractLink ret = super.newPagingNavigationLink(id, pageable, pageIndex);
//        ret.add(new SemanticActiveAttributeLinkBehavior());
        return ret;
    }

    @Override
    protected void populateItem(LoopItem loopItem) {
        // Get the index of page this link shall point to
        final long pageIndex = getStartIndex() + loopItem.getIndex();

        // Add a page link pointing to the page
        final AbstractLink link = newPagingNavigationLink(
                "pageLink",
                pageable,
                pageIndex
        );
        loopItem.add(link);
        loopItem.add(new SemanticActiveAttributeLinkBehavior(pageable, pageIndex));

        // Add a page number label to the list which is enclosed by the link
        String label;
        if (labelProvider != null) {
            label = labelProvider.getPageLabel(pageIndex);
        } else {
            label = String.valueOf(pageIndex + 1).intern();
        }
        link.add(new Label("pageNumber", label));
    }
}
