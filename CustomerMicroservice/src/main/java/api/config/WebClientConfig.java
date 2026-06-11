package api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig
 * Configuration class to create and configure WebClient bean
 * for making HTTP requests to external product service.
 * It sets the base URL for the WebClient to "http://localhost:9998".
 * and makes it available for dependency injection.
 */
@Configuration
public class WebClientConfig {

    // Create and configure a WebClient bean for the product service
    // which is accessible at "http://localhost:9998"
    @Bean
    public WebClient productWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9998")
                .build();
    }
}