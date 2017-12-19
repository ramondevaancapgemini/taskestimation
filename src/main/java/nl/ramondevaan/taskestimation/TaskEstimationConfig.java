package nl.ramondevaan.taskestimation;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.protocol.http.WebApplication;

@ApplicationInitExtension
public class TaskEstimationConfig implements
        WicketApplicationInitConfiguration {
    public void init(WebApplication webApplication) {
            webApplication
                    .getResourceSettings()
                    .getResourceFinders()
                    .add(new WebApplicationPath(
                            webApplication.getServletContext(),
                            "web"
                    ));
    }
}
