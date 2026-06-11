package api.dto;

/**
 * CustomerDTO
 * Data Transfer Object for customer.
 * Used for transferring data between client and server.
 */
public record CustomerDTO(String accountNumber, String name, String email) {
}