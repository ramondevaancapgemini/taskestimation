package nl.ramondevaan.taskestimation.web.extension;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

public class SemanticPagingNavigator extends PagingNavigator {
    public SemanticPagingNavigator(String id, IPageable pageable) {
        super(id, pageable);
    }

    public SemanticPagingNavigator(String id, IPageable pageable,
            IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }

    @Override
    protected AbstractLink newPagingNavigationIncrementLink(
            String id, IPageable pageable, int increment
    ) {
        AbstractLink ret = super.newPagingNavigationIncrementLink(id, pageable, increment);
        ret.add(new SemanticDisabledAttributeLinkBehavior());
        return ret;
    }

    @Override
    protected AbstractLink newPagingNavigationLink(
            String id, IPageable pageable, int pageNumber
    ) {
        AbstractLink ret = super.newPagingNavigationLink(id, pageable, pageNumber);
        ret.add(new SemanticDisabledAttributeLinkBehavior());
        return ret;
    }

    @Override
    protected PagingNavigation newNavigation(String id, IPageable pageable,
            IPagingLabelProvider labelProvider) {
        return new SemanticPagingNavigation(id, pageable, labelProvider);
    }


}
