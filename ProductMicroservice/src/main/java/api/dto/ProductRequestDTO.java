package api.dto;

/**
 * DTO for capturing product details from client requests.
 * Includes fields for product type and description.
 * Provides getters and setters for these fields.
 */
public class ProductRequestDTO {
    /*
     * DTO for capturing product details from client requests.
     * Includes fields for product type and description.
     * Provides getters and setters for these fields.
     */
    private String productType;
    private String productDescription;

    /* Default constructor */
    public ProductRequestDTO() {
    }

    /* Parameterized constructor to initialize all fields */
    public ProductRequestDTO(String productType, String productDescription) {
        this.productType = productType;
        this.productDescription = productDescription;
    }

    // Getters and setters for all fields
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    // toString() method for debugging purposes
    @Override
    public String toString() {
        return "ProductRequestDTO{" +
                "productType='" + productType + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }

}