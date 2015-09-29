package org.vaadin.fwteam.backend;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.fwteam.annotations.DemoComponent;
import org.vaadin.fwteam.annotations.SourceHighlight;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

@SpringComponent
@UIScope
public class DemoComponentService implements InitializingBean {

    @Autowired
    private ApplicationContext context;

    private Component demoComponent = null;
    private DemoComponent demoComponentAnnotation = null;

    public Component getComponent() {
        return demoComponent;
    }

    public String getSampleCode() {
        return "Sample code finding still work in progress..";
    }

    public String getDescription() {
        return demoComponentAnnotation.description();
    }

    public String getAuthor() {
        return demoComponentAnnotation.author();
    }

    public String getProjectName() {
        return demoComponentAnnotation.projectName();
    }

    public String getTitle() {
        return demoComponentAnnotation.title();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beans;
        beans = context.getBeansWithAnnotation(DemoComponent.class);

        if (beans.size() > 1) {
            throw new RuntimeException(
                    "Multiple DemoComponents detected. Please fix");
        } else if (beans.size() == 0) {
            throw new RuntimeException(
                    "No DemoComponents detected. Please fix");
        }
        Object bean = beans.values().iterator().next();
        if (bean instanceof Component) {
            demoComponent = (Component) bean;
            demoComponentAnnotation = demoComponent.getClass()
                    .getAnnotation(DemoComponent.class);
        } else {
            throw new RuntimeException(
                    "DemoComponent was not a Vaadin Component. Please fix");
        }
    }

    public boolean hasSourceHighlight() {
        if (demoComponent.getClass()
                .getAnnotation(SourceHighlight.class) != null) {
            return true;
        }

        for (Method m : demoComponent.getClass().getDeclaredMethods()) {
            if (m.getAnnotation(SourceHighlight.class) != null) {
                return true;
            }
        }

        return false;
    }
}
