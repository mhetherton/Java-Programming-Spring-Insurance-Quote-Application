package api.dto;

import api.model.InsuredItem;

/**
 * This DTO encapsulates an insured item along with its associated customer and
 * product details. It is used to transfer data between different layers of the
 * application. In our case the customer and product details are retrieved from
 * external microservices.
 */
public record InsuredItemWithCustomerAndProductDTO(InsuredItem insuredItem, CustomerDTO customer, ProductDTO product) {
}