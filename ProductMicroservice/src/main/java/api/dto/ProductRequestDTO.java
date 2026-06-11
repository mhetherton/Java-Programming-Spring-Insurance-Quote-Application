package api.dto;

/**
 * DTO for capturing product details from client requests.
 * Includes fields for product type and description.
 * Provides getters and setters for these fields.
 */
public record ProductRequestDTO(String productType, String productDescription) {
}