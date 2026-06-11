package api.dto;

public record InsuredItemDto(Long id, String productType, double productValue, double quoteAmount,
        String customerAccountNumber) {
}