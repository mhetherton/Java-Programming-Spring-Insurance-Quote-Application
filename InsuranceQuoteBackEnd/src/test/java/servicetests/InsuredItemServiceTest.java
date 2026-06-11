package servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import api.dto.CustomerDTO;
import api.dto.CustomerWithInsuredItemsDTO;
import api.exceptions.InvalidQuoteException;
import api.exceptions.QuoteNotFoundException;
import api.model.InsuredItem;
import api.repository.InsuredItemRepository;
import api.service.ExternalServiceClient;
import api.service.InsuredItemService;
import api.service.quotecalculations.CalculateQuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

/**
 * This test class is designed to test the functionality of the
 * InsuredItemService class.
 * It uses JUnit 5 for testing and Mockito for mocking dependencies. The tests
 * cover various scenarios for creating, retrieving, deleting, and searching
 * insured items, as well as fetching customer details along with their insured
 * items. Each test method is annotated with @Test and uses assertions to verify
 * the expected outcomes. The setUp() method is annotated with @BeforeEach to
 * initialize the necessary mocks and the service instance before each test is
 * executed. This ensures that each test runs in isolation with a fresh setup,
 * preventing interference between tests and ensuring accurate results.
 */
public class InsuredItemServiceTest {

    // Instance variables for mocks and service under test
    private InsuredItemRepository insuredItemRepository;
    private ExternalServiceClient externalServiceClient;
    private CalculateQuoteService calculateQuote;
    private InsuredItemService insuredItemService;

    /*
     * Setup method to initialize mocks and the before each test
     * We use Mockito to create mock instances of the dependencies
     * and inject them into the InsuredItemService instance
     */
    @BeforeEach
    public void setUp() {
        insuredItemRepository = mock(InsuredItemRepository.class);
        externalServiceClient = mock(ExternalServiceClient.class);
        calculateQuote = mock(CalculateQuoteService.class);
        insuredItemService = new InsuredItemService(calculateQuote, insuredItemRepository, externalServiceClient);
    }

    @Test
    void createInsuredItem_shouldSaveAndReturnItem() {
        InsuredItem item = new InsuredItem("Laptop", 1000, 120, "ACC123");
        when(insuredItemRepository.save(item)).thenReturn(item);

        InsuredItem result = insuredItemService.createInsuredItem(item);

        assertEquals(item, result);
        verify(insuredItemRepository).save(item);
    }

    @Test
    void createInsuredItem_shouldThrowIfProductTypeNull() {
        InsuredItem item = new InsuredItem(null, 1000, 120, "ACC123");
        assertThrows(InvalidQuoteException.class, () -> insuredItemService.createInsuredItem(item));
    }

    @Test
    void findInsuredItemById_shouldReturnItem() {
        InsuredItem item = new InsuredItem("Laptop", 1000, 120, "ACC123");
        when(insuredItemRepository.findById(1L)).thenReturn(Optional.of(item));

        InsuredItem result = insuredItemService.findInsuredItemById(1L);

        assertEquals(item, result);
    }

    @Test
    void findInsuredItemById_shouldThrowIfNotFound() {
        when(insuredItemRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(QuoteNotFoundException.class, () -> insuredItemService.findInsuredItemById(1L));
    }

    @Test
    void deleteInsuredItem_shouldDeleteIfExists() {
        when(insuredItemRepository.existsById(1L)).thenReturn(true);
        insuredItemService.deleteInsuredItem(1L);
        verify(insuredItemRepository).deleteById(1L);
    }

    @Test
    void deleteInsuredItem_shouldThrowIfNotExists() {
        when(insuredItemRepository.existsById(1L)).thenReturn(false);
        assertThrows(QuoteNotFoundException.class, () -> insuredItemService.deleteInsuredItem(1L));
    }

    @Test
    void getCustomerWithInsuredItems_shouldReturnDTO() {
        String accountNumber = "ACC123";
        CustomerDTO customer = new CustomerDTO(accountNumber, "John", "john@email.com");
        InsuredItem item = new InsuredItem("Laptop", 1000, 120, accountNumber);
        item.setProductType("Laptop");
        item.setProductValue(1000);
        item.setQuoteAmount(120);

        when(externalServiceClient.findCustomerByAccountNumber(accountNumber)).thenReturn(customer);
        when(insuredItemRepository.findByCustomerAccountNumber(accountNumber)).thenReturn(List.of(item));

        CustomerWithInsuredItemsDTO result = insuredItemService.getCustomerWithInsuredItems(accountNumber);

        assertEquals(customer, result.customer());
        assertEquals(1, result.insuredItems().size());
        assertEquals("Laptop", result.insuredItems().get(0).productType());
    }

    @Test
    void findByProductType_shouldReturnMatchingItems() {
        InsuredItem item1 = new InsuredItem("Laptop", 1000, 120, "ACC123");
        InsuredItem item2 = new InsuredItem("Laptop", 800, 100, "ACC124");
        when(insuredItemRepository.findAll()).thenReturn(List.of(item1, item2));

        List<InsuredItem> result = insuredItemService.findByProductType("Laptop");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(i -> i.getProductType().equalsIgnoreCase("Laptop")));
    }
}