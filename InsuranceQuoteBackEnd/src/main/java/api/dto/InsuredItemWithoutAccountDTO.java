package api.dto;

public class InsuredItemWithoutAccountDTO {
    // Private fields excluding accountNumber
    private Long id;
    private String productType;
    private double productValue;
    private double quoteAmount;

    // Default constructor
    public InsuredItemWithoutAccountDTO() {
    } // End of default constructor

    // Parameterized constructor
    public InsuredItemWithoutAccountDTO(Long id, String productType,
            double productValue, double quoteAmount) {
        this.id = id;
        this.productType = productType;
        this.productValue = productValue;
        this.quoteAmount = quoteAmount;
    }

    // Getters and setters
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

    public double getProductValue() {
        return productValue;
    }

    public void setProductValue(double productValue) {
        this.productValue = productValue;
    }

    public double getQuoteAmount() {
        return quoteAmount;
    }

    public void setQuoteAmount(double quoteAmount) {
        this.quoteAmount = quoteAmount;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "InsuredItemWithoutAccountDTO{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", productValue=" + productValue +
                ", quoteAmount=" + quoteAmount +
                '}';
    }

}