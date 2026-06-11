package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The @SpringBootApplication annotation is used to enable a number of
 * features, such as starting the auto-configuration of the Spring
 * application context, enabling Spring to find and register beans in the
 * configuration class, and enabling component scanning.
 * The @SpringBootApplication annotation is equivalent to using
 * @Configuration, @EnableAutoConfiguration, and @ComponentScan with
 * their default attributes.
 * The @SpringBootApplication annotation is used to mark the main
 * configuration class. The main configuration class is the entry point
 * for the Spring Boot application. We need to ensure that we use
 * the .class attribute to pass the class name to the
 * SpringApplication.run() method. The .class attribute means we are
 * referencing the byte code of the class not the java file code.
 */
@SpringBootApplication
public class CustomerServiceMainApplication {
    public static void main(String[] args) throws Exception {
        /*
         * The SpringApplication.run() method is used to start the
         * Spring Boot application. The SpringApplication.run() method
         * takes two arguments:
         * 1. The main configuration class
         * 2. The command-line arguments
         */
        SpringApplication.run(CustomerServiceMainApplication.class, args);
    }

}