package api.dto;

import java.util.List;

public class CustomerWithInsuredItemsDTO {
    // Private fields for customer and insured items
    private CustomerDTO customer;
    private List<InsuredItemWithoutAccountDTO> insuredItems;

    // Default constructor
    public CustomerWithInsuredItemsDTO() {
    }

    // Parameterized constructor
    public CustomerWithInsuredItemsDTO(CustomerDTO customer,
            List<InsuredItemWithoutAccountDTO> insuredItems) {
        this.customer = customer;
        this.insuredItems = insuredItems;
    }

    // Getters and setters
    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<InsuredItemWithoutAccountDTO> getInsuredItems() {
        return insuredItems;
    }

    public void setInsuredItems(List<InsuredItemWithoutAccountDTO> insuredItems) {
        this.insuredItems = insuredItems;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "CustomerWithInsuredItemsDTO{" +
                "customer=" + customer +
                ", insuredItems=" + insuredItems +
                '}';
    }

}