package api.service.quotecalculations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This class contains the logic for calculating insurance quotes based on
 * product type and product value. It uses the ProductTypeFactor and
 * ProductValueFactor classes to determine the appropriate factors for the quote
 * calculation. The calculateQuote method takes in the product type and product
 * value as parameters and returns the calculated quote. The quote is calculated
 * using the formula: (product type factor) * (product value factor) * (product
 * value / 100). The class is annotated with @Service to indicate that it is a
 * service component in the Spring framework.
 */
@Service
public class CalculateQuoteService {
    // Instantiate the ProductTypeFactor and ProductValueFactor classes
    ProductTypeFactor productTypeFactor = new ProductTypeFactor();
    ProductValueFactor productValueFactor = new ProductValueFactor();

    private static final Logger logger = LoggerFactory.getLogger(CalculateQuoteService.class);

    public double calculateQuote(String productType, double productValue) {
        logger.info("Calculating quote for productType: {}, productValue: {}", productType, productValue);

        double result = ((productTypeFactor.productTypeFactor(productType)
                * productValueFactor.productValueFactor(productValue))
                * productValue) / 100.0;

        logger.info("Calculated quote: {}", result);

        return result;
    }
}