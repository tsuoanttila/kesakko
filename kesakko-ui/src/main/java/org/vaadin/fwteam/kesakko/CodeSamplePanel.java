package org.vaadin.fwteam.kesakko;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.fwteam.backend.DemoComponentService;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class CodeSamplePanel extends Panel implements InitializingBean {

    private static final long serialVersionUID = 845984328662479779L;

    @Autowired
    private DemoComponentService service;

    public CodeSamplePanel() {
        super();

        setHeight("100%");
        setWidth("400px");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (service.hasSourceHighlight()) {
            VerticalLayout content = new VerticalLayout();
            content.setMargin(true);
            setContent(content);
            content.setSizeFull();
            BrowserFrame c = new BrowserFrame("Sample Code",
                    new ExternalResource("./source.txt"));
            c.setSizeFull();
            content.addComponent(c);
        } else {
            setVisible(false);
        }
    }
}
