package api.dto;

import api.model.InsuredItem;

/**
 * This DTO encapsulates an insured item along with its associated customer
 * details.
 * It is used to transfer data between different layers of the application. In
 * our
 * case the customer details are retrieved from an external customer
 * microservice.
 */
public class InsuredItemWithCustomerDTO {
    // Private fields for insured item and customer details
    private InsuredItem insuredItem;
    private CustomerDTO customer;

    // Default constructor
    public InsuredItemWithCustomerDTO() {
    }

    // Parameterized constructor
    public InsuredItemWithCustomerDTO(InsuredItem insuredItem,
            CustomerDTO customer) {
        this.insuredItem = insuredItem;
        this.customer = customer;
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

    // toString method for debugging
    @Override
    public String toString() {
        return "InsuredItemWithCustomerDTO{" +
                "insuredItem=" + insuredItem +
                ", customer=" + customer +
                '}';
    }
}