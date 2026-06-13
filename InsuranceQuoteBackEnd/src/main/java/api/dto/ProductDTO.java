package api.dto;

/**
 * A DTO is used to transfer data between processes. It is often used to
 * encapsulate data and send it from one subsystem of an application to another.
 * In our application we will use this ProductDTO to receive insurance product
 * details from a Product microservice and then display it. It includes fields
 * for id, product type, and product description, along with constructors,
 * getters, and setters.
 */
public record ProductDTO(Long id, String productType, String productDescription) {

}