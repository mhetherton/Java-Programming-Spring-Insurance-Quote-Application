package api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ProductClientService {

    // WebClient instance for making HTTP requests to Product microservice
    private final WebClient productWebClient;

    // Constructor injecting for the WebClient (this comees from
    // WebClientConfig.java so the base URL is already set to
    // "http://localhost:9998" i.e. the Product microservice using port 9998)
    public ProductClientService(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    /**
     * Retrieves all product types from Product microservice
     * Returns a list of product type strings
     */
    @SuppressWarnings("unchecked")
    public List<String> getAllProductTypes() {
        return productWebClient
                .get()
                .uri("/api/product/types")
                .retrieve()
                .bodyToMono(List.class)
                .block(); // Blocks to convert reactive to synchronous
    }

    /**
     * Retrieves product descriptions by product type from Product microservice
     * 
     * @param productType the type of product to search for
     *                    Returns a list of product description strings
     */
    public List<String> getProductDescriptionsByType(String productType) {
        return productWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/product/search/type")
                        .queryParam("productType", productType)
                        .build())
                .retrieve()
                .bodyToFlux(java.util.Map.class)
                .map(product -> (String) product.get("productDescription"))
                .collectList()
                .block(); // Blocks to convert reactive to synchronous
    }

}