package api.dto;

public class InsuredItemDto {
    // Fields
    private Long id;

    private String productType;

    private double productValue;

    private double quoteAmount;

    private String customerAccountNumber;

    // Default constructor
    public InsuredItemDto() {

    } // End of default constructor

    // Parameterized constructor
    public InsuredItemDto(Long id, String productType, double productValue, double quoteAmount,
            String customerAccountNumber) {
        this.id = id;
        this.productType = productType;
        this.productValue = productValue;
        this.quoteAmount = quoteAmount;
        this.customerAccountNumber = customerAccountNumber;
    }

    // Getter and Setter methods
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

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(String customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    @Override
    public String toString() {
        return "InsuredItemDto{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", productValue=" + productValue +
                ", quoteAmount=" + quoteAmount +
                ", customerAccountNumber='" + customerAccountNumber + '\'' +
                '}';
    }
}