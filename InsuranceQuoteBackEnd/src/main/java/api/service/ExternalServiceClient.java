package api.service;

import api.dto.ProductDTO;
import api.dto.CustomerDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The ExternalServiceClient class is responsible for communicating with
 * external microservices to fetch customer details and product information. It
 * uses RestTemplate to make REST API calls to the external services. The class
 * provides methods to find a customer by account number and to find products
 * by product type.
 */
@Service
public class ExternalServiceClient {

    // Create RestTemplate instance for making REST API calls
    private final RestTemplate restTemplate;

    private static final String HOST_URL = "localhost";
    private static final Integer CUSTOMER_SERVICE_PORT = 9999;
    private static final Integer PRODUCT_SERVICE_PORT = 9998;

    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceClient.class);

    /*
     * CONSTRUCTOR FOR EXTERNAL SERVICE CLIENT
     * Injects RestTemplate for making REST API calls
     */
    @Autowired
    public ExternalServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
     * GET CUSTOMER BY ACCOUNT NUMBER METHOD
     * Fetches customer details from external service using account number
     */
    public CustomerDTO findCustomerByAccountNumber(String accountNumber) {
        logger.info("Fetching customer details for account number: {}", accountNumber);
        String url = String.format("http://%s:%d/api/customer/account/%s", HOST_URL,
                CUSTOMER_SERVICE_PORT, accountNumber);
        CustomerDTO customer = restTemplate.getForObject(url, CustomerDTO.class);
        logger.info("Received customer details: {}", customer);
        return customer;
    }

    /*
     * GET PRODUCTS BY TYPE METHOD
     * Fetches products from external service using product type
     */
    public List<ProductDTO> findProductsByType(String productType) {
        logger.info("Fetching products for product type: {}", productType);
        String url = String.format("http://%s:%d/api/product/search/type?productType=%s", HOST_URL,
                PRODUCT_SERVICE_PORT, productType);
        ProductDTO[] productsArray = restTemplate.getForObject(url, ProductDTO[].class);
        logger.info("Received {} products", productsArray != null ? productsArray.length : 0);
        return List.of(productsArray);
    }
}