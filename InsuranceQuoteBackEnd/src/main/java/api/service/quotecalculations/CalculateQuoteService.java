package api.service.quotecalculations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculateQuoteService {
    // Instantiate the ProductTypeFactor and ProductValueFactor classes
    ProductTypeFactor productTypeFactor = new ProductTypeFactor();
    ProductValueFactor productValueFactor = new ProductValueFactor();

    private static final Logger logger = LoggerFactory.getLogger(CalculateQuoteService.class);

    public double calculateQuote(String productType, double productValue) {
        logger.info("Calculating quote for productType: {}, productValue: {}", productType, productValue);
        double result = (productTypeFactor.productTypeFactor(productType) *
                productValueFactor.productValueFactor(productValue) *
                productValue / 100);
        logger.info("Calculated quote: {}", result);

        return result;

    }

}