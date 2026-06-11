package api.service.quotecalculations;

import org.springframework.stereotype.Service;

@Service
public class CalculateQuote {
    // Instantiate the ProductTypeFactor and ProductValueFactor classes
    ProductTypeFactor productTypeFactor = new ProductTypeFactor();
    ProductValueFactor productValueFactor = new ProductValueFactor();

    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(CalculateQuote.class);


    public double calculateQuote(String productType, double productValue)
    {
        logger.info("Calculating quote for productType: {}, productValue: {}", productType, productValue);
        double result = (productTypeFactor.productTypeFactor(productType) *
                productValueFactor.productValueFactor(productValue) *
                productValue / 100);
        logger.info("Calculated quote: {}", result);

        return result;

    } // End of calculateQuote() method

} // End of CalculateQuote class