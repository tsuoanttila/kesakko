#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "org.vaadin.fwteam", "${groupId}" })
public class KesakkoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KesakkoDemoApplication.class, args);
    }
}
