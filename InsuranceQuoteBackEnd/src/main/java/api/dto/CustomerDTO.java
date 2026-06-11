package api.dto;

/**
 * A DTO is used to transfer data between processes. It is often used to
 * encapsulate
 * data and send it from one subsystem of an application to another.
 * In our application we will use this CustomerDTO to receive customer details
 * from a Customer microservice and then display it. It includes fields for
 * account number, name, and email, along with constructors, getters, and
 * setters.
 */
public record CustomerDTO(String accountNumber, String name, String email) {
}