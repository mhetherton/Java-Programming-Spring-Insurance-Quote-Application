package api.service.quotecalculations;

public class QuoteCalculations {
    public double productTypeFactor(String productType) {
        double productTypeFactor = 0;

        /*
         Determine the product type factor based on the product type
         If the product type is "Mobile Phone", the factor is 1.1
         If the product type is "Laptop", the factor is 1.2
         For any other product type, the factor is 1.0
        */
        switch (productType)
        {
            case "Mobile Phone":
                productTypeFactor = 1.1;
                break;
            case "Laptop":
                productTypeFactor = 1.2;
                break;
            case "Television":
                productTypeFactor = 1.3;
                break;
            default:
                productTypeFactor = 1.4;
                break;

        } // End of switch statement
        return productTypeFactor;

    } // End of productTypeFactor() method

    public double productValueFactor(double productValue)
    {
        // Check if the product value is less than or equal to zero
        if (productValue <= 0) {
            throw new IllegalArgumentException("Product value must be greater than zero");
        }
        // Check if the product value is less than or equal to 500
        return productValue <= 500 ? 1.0 : 1.2;
    } // End of productValueFactor() method

    public double calculateQuote(String productType, double productValue)
    {
        return (productTypeFactor(productType) *
                productValueFactor(productValue) *
                productValue / 100);

    } // End of calculateQuote() method


} // End of QuoteCalculations class