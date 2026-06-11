package api.service.quotecalculations;

/**
 * Determine the product type factor based on the product type
 * If the product type is "Mobile Phone", the factor is 1.1
 * If the product type is "Laptop", the factor is 1.2
 * For any other product type, the factor is 1.4
 */
public class ProductTypeFactor {
    public double productTypeFactor(String productType) {

        return switch (productType) {
            case "Mobile Phone" -> 1.1;
            case "Laptop" -> 1.2;
            case "Television" -> 1.3;
            default -> 1.4;
        };

    }
}
