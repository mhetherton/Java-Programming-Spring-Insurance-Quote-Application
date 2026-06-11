package api.dto;

import api.model.InsuredItem;

/**
 * This DTO encapsulates an insured item along with its associated customer and
 * product details. It is used to transfer data between different layers of the
 * application. In our case the customer and product details are retrieved from
 * external microservices.
 */
public class InsuredItemWithCustomerAndProductDTO {
    // The properties of the DTO class which are instances of other classes
    private InsuredItem insuredItem;
    private CustomerDTO customer;
    private ProductDTO product;

    // Default constructor
    public InsuredItemWithCustomerAndProductDTO() {
    } // End of default constructor

    // Parameterized constructor
    public InsuredItemWithCustomerAndProductDTO(InsuredItem insuredItem, CustomerDTO customer, ProductDTO product) {
        this.insuredItem = insuredItem;
        this.customer = customer;
        this.product = product;
    }

    // Getters and setters
    public InsuredItem getInsuredItem() {
        return insuredItem;
    }

    public void setInsuredItem(InsuredItem insuredItem) {
        this.insuredItem = insuredItem;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    // toString() method for easy debugging and logging
    @Override
    public String toString() {
        return "InsuredItemWithCustomerAndProductDTO{" +
                "insuredItem=" + insuredItem +
                ", customer=" + customer +
                ", product=" + product +
                '}';
    }
}