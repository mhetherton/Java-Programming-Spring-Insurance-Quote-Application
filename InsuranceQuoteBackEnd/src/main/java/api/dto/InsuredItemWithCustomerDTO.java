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
public record InsuredItemWithCustomerDTO(InsuredItem insuredItem, CustomerDTO customer) {
}