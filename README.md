kesakko
==============

Vaadin Framework Team presents: Kesakko*, The Automatic Spring Boot Demo Application Platform!

*Kesakko means a reindeer calf born this summer

How To Demo: 
- Install the jar and archetype `mvn install`
- Create your demo project `mvn archetype:generate -DarchetypeGroupId=org.vaadin.fwteam -DarchetypeArtifactId=kesakko-archetype -DarchetypeVersion=0.1-SNAPSHOT`
- Modify MyDemoComponent.java to be your demo component
- Update @DemoComponent annotation parameters
- Optionally add a @SourceHighlight to a Type or Method to show some code
- Test with `mvn clean spring-boot:run` and open http://localhost:8080/ in a browser
- Run `mvn package` to make a WAR-file
- Deploy

