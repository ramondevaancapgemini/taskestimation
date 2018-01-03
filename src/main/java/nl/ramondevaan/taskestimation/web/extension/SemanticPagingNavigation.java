package nl.ramondevaan.taskestimation.web.extension;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;

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
        ret.add(new SemanticActivePageAttributeLinkBehavior(pageable, pageIndex));
        return ret;
    }

    @Override
    protected void populateItem(LoopItem loopItem) {
        super.populateItem(loopItem);
        loopItem.setRenderBodyOnly(true);
    }
}
