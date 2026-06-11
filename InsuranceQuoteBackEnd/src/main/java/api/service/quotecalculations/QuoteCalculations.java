package api.service.quotecalculations;

/**
 * Determine the product type factor based on the product type
 * If the product type is "Mobile Phone", the factor is 1.1
 * If the product type is "Laptop", the factor is 1.2
 * For any other product type, the factor is 1.0
 */
public class QuoteCalculations {

    /*
     * Determine the product type factor based on the product type
     * If the product type is "Mobile Phone", the factor is 1.1
     * If the product type is "Laptop", the factor is 1.2
     * For any other product type, the factor is 1.0
     */
    public double productTypeFactor(String productType) {

        return switch (productType) {
            case "Mobile Phone" -> 1.1;
            case "Laptop" -> 1.2;
            case "Television" -> 1.3;
            default -> 1.4;
        };

    }

    public double productValueFactor(double productValue) {
        // Check if the product value is less than or equal to zero
        if (productValue <= 0) {
            throw new IllegalArgumentException("Product value must be greater than zero");
        }
        // Check if the product value is less than or equal to 500
        return productValue <= 500 ? 1.0 : 1.2;
    }

    public double calculateQuote(String productType, double productValue) {
        return (productTypeFactor(productType) *
                productValueFactor(productValue) *
                productValue / 100);
    }
}