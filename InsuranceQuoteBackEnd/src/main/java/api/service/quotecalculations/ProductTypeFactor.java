package api.service.quotecalculations;

public class ProductTypeFactor {
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
} // End of ProductTypeFactor class
