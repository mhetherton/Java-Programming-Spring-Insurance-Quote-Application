package api.service;

import api.dto.*;
import api.model.InsuredItem;
import api.repository.InsuredItemRepository;
import api.service.quotecalculations.CalculateQuote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import api.exceptions.InvalidQuoteException;
import api.exceptions.QuoteNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Service annotation is used to mark the class as a service
 *          provider. The @Service annotation is a specialization of the
 * @Component annotation. It's a good practice to use @Service over
 * @Component in service-layer classes
 */
@Service
public class InsuredItemService {

    /*
     * DEPENDENCY INJECTION
     * Inject CalculateQuote, InsuredItemRepository, and
     * ExternalServiceClient for business logic, data access,
     * and external service integration
     */
    private final CalculateQuote calculateQuote;
    private final InsuredItemRepository insuredItemRepository;
    private final ExternalServiceClient externalServiceClient;

    // Logger for logging information, warnings, and errors
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExternalServiceClient.class);

    /*
     * CONSTRUCTOR-BASED DEPENDENCY INJECTION
     * Inject CalculateQuote, InsuredItemRepository,
     * and ExternalServiceClient into InsuredItemService
     */
    public InsuredItemService(CalculateQuote calculateQuote,
            InsuredItemRepository insuredItemRepository,
            ExternalServiceClient externalServiceClient) {
        this.calculateQuote = calculateQuote;
        this.insuredItemRepository = insuredItemRepository;
        this.externalServiceClient = externalServiceClient;
    }

    /*
     * CREATE
     * Create a new InsuredItem by accepting an InsuredItem object
     * If the productType is null, throw an InvalidQuoteException.
     * Save the InsuredItem object to the database using save()
     * from the repository. To confirm the item has been saved
     * we return the saved InsuredItem object to the calling
     * method which will be in the controller class.
     */
    public InsuredItem createInsuredItem(InsuredItem insuredItem) {
        logger.debug("Creating insured item: {}", insuredItem);
        if (insuredItem.getProductType() == null) {
            throw new InvalidQuoteException("Invalid InsuredItem: productType must not be null");
        }
        InsuredItem savedItem = insuredItemRepository.save(insuredItem);
        logger.info("Insured item created successfully: {}", insuredItem.getId());
        return savedItem;
    }

    /*
     * READ
     * Read all InsuredItem records from the database using the
     * findAll() method which is provided by the JpaRepository
     * interface, which is extended by the InsuredItemRepository
     * interface. This method retrieves all the records from the
     * InsuredItem table in the database and returns them as a
     * list of InsuredItem objects, to the calling method
     * which will be in the controller class.
     */
    public List<InsuredItem> findAllInsuredItems() {
        return insuredItemRepository.findAll();
    }

    /*
     * UPDATE
     * Update an existing InsuredItem by accepting an id an
     * InsuredItem object. If the productType is null, throw an
     * InvalidQuoteException. First, we retrieve the existing
     * InsuredItem by id using the getInsuredItemById() method.
     * Instead of using the findById() method of Jpa directly, we
     * use findInsuredItemById() to encapsulate the logic of
     * checking if the InsuredItem with the given id exists.
     */
    public InsuredItem updateInsuredItem(Long id, InsuredItem insuredItem) {
        if (insuredItem.getProductType() == null) {
            throw new InvalidQuoteException("Invalid InsuredItem: productType must not be null");
        }
        InsuredItem existing = findInsuredItemById(id);
        existing.setProductType(insuredItem.getProductType());
        existing.setProductValue(insuredItem.getProductValue());
        existing.setQuoteAmount(insuredItem.getQuoteAmount());

        return insuredItemRepository.save(existing);
    }

    /*
     * DELETE
     * Delete an InsuredItem by accepting an id. If the
     * InsuredItem with the given id does not exist, a
     * QuoteNotFoundException will be thrown. We check if the
     * InsuredItem with the given id exists using the existsById()
     * method from the repository. If it does not exist, we throw
     * a QuoteNotFoundException. If it exists, we delete it using
     * the deleteById() method from the repository. This method
     * does not return anything to the calling method which will
     * be in the controller class.
     * We could simply use the Jpa method findById() to check if
     * the InsuredItem exists but using existsById() is more
     * efficient as it only checks for existence without
     * retrieving the entire entity. This is especially useful
     * when we only need to check if the entity exists before
     * performing a delete operation.
     */
    public void deleteInsuredItem(Long id) {
        if (!insuredItemRepository.existsById(id)) {
            throw new QuoteNotFoundException("InsuredItem not found with id: " + id);
        }
        insuredItemRepository.deleteById(id);
    }

    /*
     * READ
     * This method retrieves an InsuredItem by its id. If the
     * InsuredItem with the given id does not exist, a
     * QuoteNotFoundException is thrown. We use the findById()
     * method from the repository to retrieve the InsuredItem.
     * If it exists, we return the InsuredItem object to the
     * calling method which will be in the controller class.
     */
    public InsuredItem findInsuredItemById(Long id) {
        return insuredItemRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException("InsuredItem not found with id: " + id));
    }

    /*
     * CALCULATE AND SAVE QUOTE
     * Calculate quote based on product type and value, then
     * save the InsuredItem record to the DB
     */
    public InsuredItem createAndSaveInsuredItem(String productType,
            double productValue, String customerAccountNumber) {
        /*
         * Calculate the quote amount by passing productType and productValue
         * to the calculateQuote method of the CalculateQuote service
         */
        double quoteAmount = calculateQuote.calculateQuote(productType, productValue);

        // Create a new InsuredItem object and set its fields
        InsuredItem insuredItem = new InsuredItem();
        insuredItem.setProductType(productType);
        insuredItem.setProductValue(productValue);
        insuredItem.setQuoteAmount(quoteAmount);
        insuredItem.setCustomerAccountNumber(customerAccountNumber);

        // Save the InsuredItem object to the database and return the saved object
        return insuredItemRepository.save(insuredItem);
    }

    /*
     * GET CUSTOMER DETAILS WITH INSURED ITEMS
     * Retrieve Customer along with their
     * InsuredItem by customer account number
     */
    public CustomerWithInsuredItemsDTO getCustomerWithInsuredItems(String accountNumber) {
        // Fetch customer details from external service
        CustomerDTO customer = externalServiceClient.findCustomerByAccountNumber(accountNumber);

        if (customer == null) {
            throw new QuoteNotFoundException("Customer not found with account number: " + accountNumber);
        } // End of if block

        /*
         * Here we retrieve all InsuredItem entities for the given
         * customer account number, then we use the Java Streams API
         * to map each entity to an InsuredItemWithoutAccountDTO.
         * - The repository method findByCustomerAccountNumber
         * (accountNumber) returns a List<InsuredItem>.
         * - .stream() creates a stream from this list, allowing
         * functional-style operations.
         * - .map(...) transforms each InsuredItem into a new
         * InsuredItemWithoutAccountDTO, passing only the relevant
         * fields (id, productType, productValue, quoteAmount)
         * to the DTO constructor.
         * - The DTO intentionally excludes the account number,
         * since it is already present in the Customer object.
         * - .toList() collects all mapped DTOs into a new
         * List<InsuredItemWithoutAccountDTO>.
         * This approach makes the code concise, readable, and
         * ensures the API response does not contain redundant data.
         */
        List<InsuredItemWithoutAccountDTO> itemDTOs = insuredItemRepository
                .findByCustomerAccountNumber(accountNumber)
                .stream()
                .map(item -> new InsuredItemWithoutAccountDTO(
                        item.getId(),
                        item.getProductType(),
                        item.getProductValue(),
                        item.getQuoteAmount()))
                .toList();

        return new CustomerWithInsuredItemsDTO(customer, itemDTOs);
    }

    /*
     * FILTER INSURED ITEMS BY PRODUCT TYPE
     * Retrieve InsuredItem records matching the specified
     * product type (case-insensitive)
     */
    public List<InsuredItem> findByProductType(String productType) {
        /*
         * Use Java Streams to filter InsuredItem records by
         * productType in a case-insensitive manner. We retrieve
         * all InsuredItem records using findAll(), then create a
         * stream from the list. We apply a filter to include only
         * those items where the productType matches the input
         * (ignoring case). Finally, we collect the filtered items
         * back into a list using toList() and return it.
         */
        return insuredItemRepository.findAll().stream()
                .filter(item -> item.getProductType().equalsIgnoreCase(productType))
                .toList();
    }

    /**********************************************************
     ****************** DERIVED QUERIES ***********************
     *********************************************************/

    // Search by product value equal to a specified value
    public List<InsuredItem> findByProductValue(double productValue) {
        List<InsuredItem> results = insuredItemRepository.findByProductValue(productValue);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException("InsuredItem not found with productValue equal to: " + productValue);
        }
        return results;
    }

    // Search by product value greater than a specified value
    public List<InsuredItem> findByProductValueGreaterThan(double productValue) {
        List<InsuredItem> results = insuredItemRepository.findByProductValueGreaterThan(productValue);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException("InsuredItem not found with productValue greater than: " + productValue);
        }
        return results;
    }

    // Search by product value less than a specified value
    public List<InsuredItem> findByProductValueLessThan(double productValue) {
        List<InsuredItem> results = insuredItemRepository.findByProductValueLessThan(productValue);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException("InsuredItem not found with productValue less than: " + productValue);
        }
        return results;
    }

    // Search by product type and product value
    public List<InsuredItem> findByProductTypeAndProductValue(String productType, double productValue) {
        List<InsuredItem> results = insuredItemRepository.findByProductTypeAndProductValue(productType, productValue);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException(
                    "InsuredItem not found with productType: " + productType + " and productValue: " + productValue);
        }
        return results;
    }

    // Search by product type or product value
    public List<InsuredItem> findByProductTypeOrProductValue(String productType, double productValue) {
        List<InsuredItem> results = insuredItemRepository.findByProductTypeOrProductValue(productType, productValue);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException(
                    "InsuredItem not found with productType: " + productType + " or productValue: " + productValue);
        }
        return results;
    }

    // Search by product value between two values
    public List<InsuredItem> findByProductValueBetween(double startValue, double endValue) {
        List<InsuredItem> results = insuredItemRepository.findByProductValueBetween(startValue, endValue);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException(
                    "InsuredItem not found with productValue between: " + startValue + " and " + endValue);
        }
        return results;
    }

    // Search by product type like a string value where the case is ignored
    public List<InsuredItem> findByProductTypeLikeIgnoreCase(String pattern) {
        List<InsuredItem> results = insuredItemRepository.findByProductTypeLikeIgnoreCase(pattern);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException("InsuredItem not found with productType like: " + pattern);
        }
        return results;
    }

    // Search by product type which is in the provided list
    public List<InsuredItem> findByProductTypeIn(List<String> productTypes) {
        List<InsuredItem> results = insuredItemRepository.findByProductTypeIn(productTypes);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException("InsuredItem not found with productTypes in: " + productTypes);
        }
        return results;
    }

    // Search by product type and order the list of results in descending order by
    // product value
    public List<InsuredItem> findByProductTypeOrderByProductValueDesc(String productType) {
        List<InsuredItem> results = insuredItemRepository.findByProductTypeOrderByProductValueDesc(productType);
        if (results.isEmpty()) {
            throw new QuoteNotFoundException(
                    "InsuredItem not found with productType: " + productType + " ordered by productValue descending");
        }
        return results;
    }

    public List<CustomerWithInsuredItemsDTO> getAllCustomersWithInsuredItems() {
        List<InsuredItem> items = insuredItemRepository.findAll();

        // Group insured items by customer account number
        Map<String, List<InsuredItem>> itemsByCustomer = items.stream()
                .collect(Collectors.groupingBy(InsuredItem::getCustomerAccountNumber));

        List<CustomerWithInsuredItemsDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<InsuredItem>> entry : itemsByCustomer.entrySet()) {
            String accountNumber = entry.getKey();
            List<InsuredItem> insuredItems = entry.getValue();

            // Fetch customer details from external microservice
            CustomerDTO customer = externalServiceClient.findCustomerByAccountNumber(accountNumber);

            // Map insured items to DTOs (excluding account number if needed)
            List<InsuredItemWithoutAccountDTO> itemDTOs = insuredItems.stream()
                    .map(item -> new InsuredItemWithoutAccountDTO(
                            item.getId(),
                            item.getProductType(),
                            item.getProductValue(),
                            item.getQuoteAmount()))
                    .collect(Collectors.toList());

            // Combine customer and their insured items
            result.add(new CustomerWithInsuredItemsDTO(customer, itemDTOs));
        }

        return result;
    }

    // Service method to get all insured items with customer and product details
    public List<InsuredItemWithCustomerAndProductDTO> getAllInsuredItemsWithCustomerAndProductDetails() {
        List<InsuredItem> items = findAllInsuredItems();
        return items.stream()
                .map(item -> {
                    CustomerDTO customer = externalServiceClient
                            .findCustomerByAccountNumber(item.getCustomerAccountNumber());
                    List<ProductDTO> products = externalServiceClient.findProductsByType(item.getProductType());
                    ProductDTO product = products.isEmpty() ? null : products.get(0); // Use first product or handle as
                                                                                      // needed
                    return new InsuredItemWithCustomerAndProductDTO(item, customer, product);
                })
                .toList();
    }

    /*
     * READ WITH PAGINATION
     * Read all InsuredItem records with pagination. This method
     * accepts page number and page size as parameters to control
     * the pagination. We create a Pageable object using
     * PageRequest.of(page, size) which specifies the page
     * number and the size of each page. We then pass this
     * Pageable object to the findAll() method of the repository.
     * The findAll() method returns a Page<InsuredItem> object
     * which contains the content for the requested page along
     * with pagination metadata such as total pages, total
     * elements, etc. This Page<InsuredItem> object is then
     * returned to the calling method which will be in the
     * controller class.
     */
    public Page<InsuredItem> findAllInsuredItemsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return insuredItemRepository.findAll(pageable);
    }

    /*
     * READ WITH PAGINATION AND SORTING
     * Reads all InsuredItem records with pagination and sorting.
     * Accepts page number, page size, and sort parameters.
     * Handles sort parameters in two formats:
     * - Each element as "field,direction" (e.g., "productType,desc")
     * - Pairs of "field" and "direction" (e.g., "productType", "desc")
     * If direction is missing, defaults to ascending.
     * If sort parameters are null or empty, defaults to sorting by
     * "productType" ascending.
     * Returns a Page<InsuredItem> containing paginated & sorted results.
     */
    public Page<InsuredItem> findAllInsuredItemsWithPaginationAndSort(int page, int size, List<String> sortParams) {
        logger.info("Fetching insured items with pagination - page: {}, size: {}, sort: {}", page, size, sortParams);
        Sort sort;
        List<Sort.Order> orders = new java.util.ArrayList<>();

        if (sortParams != null && !sortParams.isEmpty()) {
            // If all elements do not contain a comma, treat as pairs: field, direction
            boolean allNoComma = sortParams.stream().noneMatch(s -> s.contains(","));
            if (allNoComma && sortParams.size() % 2 == 0) {
                for (int i = 0; i < sortParams.size(); i += 2) {
                    String field = sortParams.get(i).trim();
                    String direction = sortParams.get(i + 1).trim();
                    Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    orders.add(new Sort.Order(dir, field));
                }
            } else {
                // Default: each param is "field,direction"
                for (String param : sortParams) {
                    if (param == null || param.trim().isEmpty())
                        continue;
                    int commaIndex = param.indexOf(',');
                    String field, direction;
                    if (commaIndex != -1) {
                        field = param.substring(0, commaIndex).trim();
                        direction = param.substring(commaIndex + 1).trim();
                    } else {
                        field = param.trim();
                        direction = "asc";
                    }
                    Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    orders.add(new Sort.Order(dir, field));
                }
            }
            sort = orders.isEmpty() ? Sort.by(Sort.Order.asc("productType")) : Sort.by(orders);
        } else {
            sort = Sort.by(Sort.Order.asc("productType"));
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        logger.info("Fetched page of insured items");
        return insuredItemRepository.findAll(pageable);
    }

    // Search insured items by product type, customer account number, and product
    // value We can use all or some of the parameters to filter the results
    public List<InsuredItem> searchInsuredItems(String productType, String customerAccountNumber, Double productValue) {
        return insuredItemRepository.findByProductTypeAndCustomerAccountNumberAndProductValue(productType,
                customerAccountNumber, productValue);
    }

    // Finds insured items with productValue between two values
    // and productType matching a pattern
    public List<InsuredItem> findByProductValueRangeAndTypePattern(double minValue, double maxValue,
            String typePattern) {
        return insuredItemRepository.findByProductValueRangeAndTypePattern(minValue, maxValue, typePattern);
    }

}