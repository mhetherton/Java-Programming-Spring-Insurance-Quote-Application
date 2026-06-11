package api.dto;

/**
 * A DTO is used to transfer data between processes. It is often used to
 * encapsulate
 * data and send it from one subsystem of an application to another.
 * In our application we will use this ProductDTO to receive insurance product
 * details from a Product microservice and then display it. It includes fields
 * for
 * id, product type, and product description, along with constructors, getters,
 * and setters.
 */
public class ProductDTO {
    private Long id;
    private String productType;
    private String productDescription;

    // Default constructor
    public ProductDTO() {
    }

    // Parameter constructor
    public ProductDTO(Long productId, String productType, String productDescription) {
        this.id = productId;
        this.productType = productType;
        this.productDescription = productDescription;
    }

    // Getters and setters for each private field
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

}