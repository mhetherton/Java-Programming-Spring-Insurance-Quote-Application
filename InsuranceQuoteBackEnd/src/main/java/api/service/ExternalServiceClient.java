package api.service;

import api.dto.ProductDTO;
import api.dto.CustomerDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExternalServiceClient {

    // Create RestTemplate instance for making REST API calls
    private final RestTemplate restTemplate;

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
     * Fetches customer details from external service using
     * account number
     */
    public CustomerDTO findCustomerByAccountNumber(String accountNumber) {
        logger.info("Fetching customer details for account number: {}", accountNumber);
        String url = "http://localhost:9999/api/customer/account/" + accountNumber;
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
        String url = "http://localhost:9998/api/product/search/type?productType=" + productType;
        ProductDTO[] productsArray = restTemplate.getForObject(url, ProductDTO[].class);
        logger.info("Received {} products", productsArray != null ? productsArray.length : 0);
        return List.of(productsArray);
    }
}