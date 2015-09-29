#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.vaadin.fwteam.annotations.DemoComponent;

import com.vaadin.ui.VerticalLayout;

@DemoComponent(projectName = "MyGHProject", author = "me", description = "Empty demo components", title = "Title of demo page")
public class MyDemoComponent extends VerticalLayout {

    public MyDemoComponent() {
    }
}
