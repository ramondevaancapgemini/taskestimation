package nl.ramondevaan.taskestimation;

import nl.ramondevaan.taskestimation.web.HomePage;
import nl.ramondevaan.taskestimation.web.developer.DeveloperAdd;
import org.apache.wicket.Page;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class WicketApplication extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();

        getResourceSettings().getResourceFinders()
                .add(new WebApplicationPath(getServletContext(), "web"));

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                SpringConfig.class);

        getComponentInstantiationListeners()
                .add(new SpringComponentInjector(this, ctx));
    }
}
