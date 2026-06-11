package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The @SpringBootApplication annotation is used to enable a host of features,
 * such as starting the auto-configuration of the Spring application context,
 * enabling Spring to find and register beans in the configuration class, and
 * enabling component scanning.
 * The @SpringBootApplication annotation is equivalent to using @Configuration,
 * @EnableAutoConfiguration, and @ComponentScan with their default attributes.
 * The @SpringBootApplication annotation is used to mark the main configuration
 * class. The main configuration class is the entry point for the Spring Boot
 * application. We need to ensure that we use the .class attribute to pass the
 * class name to the SpringApplication.run() method. The .class attribute means
 * we are referencing the byte code of the class not the java file code.
 */
@SpringBootApplication
public class InsuredQuoteMainApplication {
    public static void main(String[] args) throws Exception {
        /*
         * The SpringApplication.run() method is used to start the
         * Spring Boot application. The SpringApplication.run() method
         * takes two arguments:
         * 1. The main configuration class
         * 2. The command-line arguments
         */
        SpringApplication.run(InsuredQuoteMainApplication.class, args);
    }

    /*
     * The RestTemplate class is used to make REST API calls to
     * external services. The RestTemplate class is a synchronous
     * client to perform HTTP requests and consume RESTful web services.
     * It is part of the Spring Framework and is used to make REST API
     * calls to external services.
     * We annotate the restTemplate() method with the @Bean annotation
     * to indicate that this method will return a bean to be managed
     * by the Spring container.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}