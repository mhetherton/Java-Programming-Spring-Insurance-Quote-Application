package api.dto;

import java.io.Serializable;

/**
 * CustomerDTO
 * Data Transfer Object for customer.
 * Used for transferring data between client and server.
 */
public record CustomerDTO(String accountNumber, String name, String email) implements Serializable {

    private static final long serialVersionUID = 1L;
}