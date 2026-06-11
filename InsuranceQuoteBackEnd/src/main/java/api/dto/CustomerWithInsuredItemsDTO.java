package api.dto;

import java.util.List;

public record CustomerWithInsuredItemsDTO(CustomerDTO customer, List<InsuredItemWithoutAccountDTO> insuredItems) {

}