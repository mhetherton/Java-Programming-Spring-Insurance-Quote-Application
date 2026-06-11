package api.dto;

/**
 * DTO for sending product details in API responses. The DTO includes
 * fields for product ID, type, and description, the same as the Product model.
 * The DTO provides a constructor and getters for these fields.
 */
public record ProductResponseDTO(Long id, String productType, String productDescription) {
}