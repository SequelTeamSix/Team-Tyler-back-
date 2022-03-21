package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

<<<<<<< HEAD

@SpringBootApplication
public class Application {


=======
/*review:
spring boot introduces some amount of automatic configuration
which replaces the need for us to have our own ApplicationContext
what's the ApplicationContext?
The ApplicationContext is the extension of the BeanFactory,
which manages our IOC - inversion of control,
which is the concept that we are surrendering the control of our dependencies
to Spring - what this means practically is that we do instantiate dependencies
(eg repository a service is dependent on)
Rather, Spring manages our classes as beans, allowing them to be candidates
for Dependency Injection
 */
@SpringBootApplication
public class Application {

    /*Spring in general:
    Spring is described typically either as framework or an ecosystem of modules
    it reduces repetitive, boilerplate java code as much as possible
    so that we can focus on business logic
    At the core of spring, we have the core spring module that all of the rest of spring
    depends on: this is what grants spring its dependency injection capability
     */

    /*
    the springbootapplication annotation has removed the need for
    three other annotations, and allows us to reduce our main method to a single
    line
    to see the difference, take a look at how we used the ApplicationContext,
    as well as the xml configuration file,
    on monday
     */
>>>>>>> 364d20e1308109e69c400f2dd3f1fec4786a8283
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}