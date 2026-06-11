package api.dto;

/**
 * DTO for sending product details in API responses. The DTO includes
 * fields for product ID, type, and description, the same as the Product model.
 * The DTO provides a constructor and getters for these fields.
 */
public class ProductResponseDTO {
    // Fields corresponding to the Product model
    private Long id;
    private String productType;
    private String productDescription;

    /*
     * Parameterized constructor to initialize all fields of the ProductResponseDTO.
     */
    public ProductResponseDTO(Long id, String productType, String productDescription) {
        this.id = id;
        this.productType = productType;
        this.productDescription = productDescription;
    }

    // Getters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return "ProductResponseDTO{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}