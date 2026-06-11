package api.service.quotecalculations;

public class ProductValueFactor {
    public double productValueFactor(double productValue)
    {
        // Check if the product value is less than or equal to zero
        if (productValue <= 0) {
            throw new IllegalArgumentException("Product value must be greater than zero");
        }
        // Check if the product value is less than or equal to 500
        return productValue <= 500 ? 1.0 : 1.2;
    } // End of productValueFactor() method
} // End of ProductValueFactor class