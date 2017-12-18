package nl.ramondevaan.taskestimation.web;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

@WicketHomePage
public class HomePage extends WebPage{
    public HomePage() {
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        // Add a form with an onSumbit implementation that sets a message
        Form<Integer> form = new Form<Integer>("form")
        {
            @Override
            protected void onSubmit()
            {
                info("Form.onSubmit executed");
            }
        };

        Button button1 = new Button("button1")
        {
            @Override
            public void onSubmit()
            {
                info("button1.onSubmit executed");
            }
        };
        form.add(button1);

        Button button2 = new Button("button2")
        {
            @Override
            public void onSubmit()
            {
                info("button2.onSubmit executed");
            }
        };
        button2.setDefaultFormProcessing(false);
        form.add(button2);

        add(form);
    }
}
