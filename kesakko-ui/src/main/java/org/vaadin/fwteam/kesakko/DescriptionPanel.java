package org.vaadin.fwteam.kesakko;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.fwteam.backend.DemoComponentService;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class DescriptionPanel extends Panel implements InitializingBean {

    private static final long serialVersionUID = 4680572753772320424L;

    @Autowired
    private DemoComponentService service;

    @Override
    public void afterPropertiesSet() throws Exception {
        VerticalLayout l = new VerticalLayout();
        l.setMargin(true);
        l.addComponent(new Label(service.getDescription()));
        String sourceURL = "http://github.com/" + service.getAuthor() + "/"
                + service.getProjectName();
        l.addComponent(new Link(sourceURL, new ExternalResource(sourceURL)));
        setContent(l);

        setSizeFull();
    }

}
