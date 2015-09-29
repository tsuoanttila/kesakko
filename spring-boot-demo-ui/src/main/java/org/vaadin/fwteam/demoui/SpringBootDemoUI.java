package org.vaadin.fwteam.demoui;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.fwteam.backend.DemoComponentService;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class SpringBootDemoUI extends UI {

    private static final long serialVersionUID = -9125772161428013919L;

    @Autowired
    private DescriptionPanel descriptionPanel;

    @Autowired
    private CodeSamplePanel codeSamplePanel;

    @Autowired
    private DemoComponentService service;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle(service.getTitle());

        HorizontalLayout horizLayout = new HorizontalLayout();

        // Setup demo container
        VerticalLayout demoContent = new VerticalLayout();
        demoContent.setMargin(true);
        demoContent.addComponent(service.getComponent());

        // Set up description and code sample tabsheet
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(descriptionPanel, "Description");
        if (codeSamplePanel.isVisible()) {
            tabSheet.addTab(codeSamplePanel, "Code Sample");
        }

        // Add to main layout
        horizLayout.addComponent(demoContent);
        horizLayout.addComponent(tabSheet);

        horizLayout.setExpandRatio(demoContent, 1.0f);
        horizLayout.setSpacing(true);

        tabSheet.setWidth("400px");
        tabSheet.setHeight("100%");
        demoContent.setSizeFull();
        horizLayout.setSizeFull();

        setContent(horizLayout);
    }
}
