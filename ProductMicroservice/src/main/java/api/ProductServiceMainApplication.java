package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceMainApplication {
    public static void main(String[] args) {
        /*
         * The `SpringApplication.run() method is used to launch
         * the Spring Boot application initializing the Spring application context,
         * which sets up all beans, and starts the embedded web server.
         * The .class reference points to the compiled class object, which
         * Spring Boot uses to bootstrap the application.
         */
        SpringApplication.run(ProductServiceMainApplication.class, args);
    }

}