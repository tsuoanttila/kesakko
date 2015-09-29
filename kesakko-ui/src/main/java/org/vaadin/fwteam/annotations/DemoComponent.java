package org.vaadin.fwteam.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringComponent
@UIScope
public @interface DemoComponent {
    /** GitHub username */
    String author();

    /** GitHub project name */
    String projectName();

    /** Description of the demo */
    String description();

    /** Demo page title */
    String title();
}
