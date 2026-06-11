package api.controller;

import api.dto.CustomerWithInsuredItemsDTO;
import api.dto.InsuredItemWithCustomerAndProductDTO;
import api.exceptions.InvalidQuoteException;
import api.exceptions.QuoteNotFoundException;
import api.model.InsuredItem;
import api.service.InsuredItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.constraints.Pattern;

/**
 * The @CrossOrigin annotation is used to handle the Cross-Origin
 * Resource Sharing (CORS) issue. It is used to allow cross-origin
 * requests from a different domain. The @CrossOrigin annotation is
 * used at the class level to enable CORS for the entire controller.
 * The origins attribute is used to specify the list of origins that are
 * allowed to access the resources.
 * The allowedHeaders attribute is used to specify the list of headers
 * that are allowed in the request.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class InsuredItemController {
    // The InsuredItemService is this class's dependency.
    private final InsuredItemService insuredItemService;

    // Create a logger instance for this class
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InsuredItemController.class);

    /*
     * The @Autowired annotation is used to automatically inject the
     * ProductService bean into the ProductController class.
     * The ProductService bean is used to perform CRUD operations on
     * the Product entity.
     * The ProductService class implements the ProductService interface.
     * The ProductService interface defines the methods that are used to
     * perform CRUD operations on the Product entity.
     * The @Autowired annotation is not strictly needed in this code because
     * we are using constructor injection and in Spring, if a class has only
     * one constructor, Spring will automatically use it for dependency
     * injection, even without the @Autowired annotation.
     */
    @Autowired
    public InsuredItemController(InsuredItemService insuredItemService) {
        this.insuredItemService = insuredItemService;
    }

    /**************************************************************
     ********************* CREATE A NEW ITEM **********************
     *************************************************************/
    /*
     * The @PostMapping annotation is used to map HTTP POST requests to
     * specific handler methods. The @RequestBody annotation binds the
     * request body with a method parameter. The @RequestBody annotation
     * indicates that a method parameter should be bound to the body of
     * the web request. The @PostMapping annotation ("/") maps HTTP POST
     * requests to the createProduct method. The createProduct method
     * creates a new product and returns the created product.
     * http://localhost:8888/ is the URL to access this method
     */
    @Operation(summary = "Create insured item", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InsuredItem.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @ResponseStatus(HttpStatus.CREATED) // optional but helps generators
    @PostMapping
    public ResponseEntity<InsuredItem> createInsuredItem(@Valid @RequestBody InsuredItem insuredItem) {
        logger.info("Received request to create insured item: {}", insuredItem);
        /*
         * Use a try-catch block to handle exceptions. If an exception
         * occurs, return a ResponseEntity with a BAD_REQUEST status.
         * If the item is created successfully, return a ResponseEntity
         * with a CREATED status and the created item in the response body.
         */
        try {
            InsuredItem createdItem = insuredItemService.createInsuredItem(insuredItem);
            logger.info("Successfully created insured item: {}", createdItem);
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        } catch (InvalidQuoteException ex) {
            logger.error("Failed to create insured item due to invalid quote: {}", ex.getMessage(), ex);
            return ResponseEntity.badRequest().build();
        }
    }

    /**************************************************************
     *********************** READ ALL ITEMS ***********************
     *************************************************************/
    /*
     * The @GetMapping annotation is used to map HTTP GET requests to
     * specific handler methods. The @GetMapping annotation ("/") maps
     * HTTP GET requests to the findAllInsuredItem method.
     * The findAllInsuredItem method returns a list of all
     * electrical items.
     * http://localhost:8888/ is the URL to access this method
     */
    @GetMapping
    public List<InsuredItem> findAllInsuredItem() {
        logger.info("Fetching all insured items");
        List<InsuredItem> items = insuredItemService.findAllInsuredItems();
        logger.info("Found {} insured items", items.size());
        return items;
    }

    /*
     * The @PutMapping annotation is used to map HTTP PUT requests to
     * specific handler methods. The @RequestBody annotation binds the
     * request body with a method parameter. The @PutMapping annotation
     * ("/{id}") maps HTTP PUT requests to the updateInsuredItem
     * method, where {id} is a URI template variable.
     * The @PathVariable annotation binds this URI template variable to
     * the method parameter id. The updateProduct method updates the
     * product with the specified id and returns the updated product.
     * // http://localhost:8888/2 is a URL to access this method
     */
    @PutMapping("/{id}")
    public ResponseEntity<InsuredItem> updateInsuredItem(@PathVariable Long id,
            @RequestBody InsuredItem insuredItem) {
        /*
         * Use a try-catch block to handle exceptions. If an exception
         * occurs, return a ResponseEntity with a NOT_FOUND status.
         * If the item is updated successfully, return a ResponseEntity
         * with an OK status and the updated item in the response body.
         */
        try {
            InsuredItem updatedItem = insuredItemService.updateInsuredItem(id,
                    insuredItem);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (InvalidQuoteException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * The @DeleteMapping annotation is used to map HTTP DELETE requests
     * to specific handler methods. The @DeleteMapping annotation ("/{id}")
     * maps HTTP DELETE requests to the deleteInsuredItem method,
     * where {id} is a URI template variable. The @PathVariable annotation
     * binds this URI template variable to the method parameter id. The
     * deleteInsuredItem method deletes the electrical item with
     * the specified id and returns a response entity with a message.
     * http://localhost:8888/2 is a URL to access this method
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInsuredItem(@PathVariable Long id) {
        try {
            insuredItemService.deleteInsuredItem(id);
            return new ResponseEntity<>("Record deleted!", HttpStatus.OK);
        } catch (QuoteNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /*
     * The @GetMapping annotation is used to map HTTP GET requests to
     * specific handler methods. The @GetMapping annotation ("/{id}") maps
     * HTTP GET requests to the getInsuredItemById method, where
     * {id} is a URI template variable. The @PathVariable annotation binds
     * this URI template variable to the method parameter id. The
     * getInsuredItemById method returns the electrical item with
     * the specified id.
     * // http://localhost:8888/2 is a URL to access this method
     */
    @GetMapping("/{id}")
    public ResponseEntity<InsuredItem> getInsuredItemById(@PathVariable Long id) {
        /*
         * Use a try-catch block to handle exceptions. If an exception
         * occurs, return a ResponseEntity with a NOT_FOUND status.
         * If the item is found successfully, return a ResponseEntity
         * with an OK status and the found item in the response body.
         */
        try {
            InsuredItem item = insuredItemService.findInsuredItemById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (QuoteNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * The @GetMapping annotation is used to map HTTP GET requests to
     * specific handler methods. The @GetMapping annotation
     * ("/searchproducttype") maps HTTP GET requests to the
     * searchInsuredItemByProductType method. The
     * searchInsuredItemByProductType method returns a list
     * of electrical items with the specified product type.
     * // http://localhost:8888/searchproducttype?producttype=laptop
     * is a URL to access this method
     */
    @GetMapping("/searchproducttype")
    public ResponseEntity<List<InsuredItem>> searchInsuredItemByProductType(
            @RequestParam(value = "producttype") String productType) {
        List<InsuredItem> results = insuredItemService.findByProductType(productType);
        /*
         * Use an if-else statement to check if the results list is empty.
         * If it is empty, return a ResponseEntity with a NOT_FOUND status.
         * If it is not empty, return a ResponseEntity with an OK status
         * and the results list in the response body.
         */
        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    /*
     * This endpoint calculates the quote and creates a new insured item
     * in one operation. It accepts query parameters for product type,
     * product value, and customer account number.
     * http://localhost:8888/createandsave?productType=Laptop&productValue=800&
     * customerAccountNumber=ACC123
     */
    @PostMapping("/createandsave")
    public ResponseEntity<InsuredItem> createAndSaveInsuredItem(
            @RequestParam @NotBlank(message = "Product type is required") String productType,
            @RequestParam @Positive(message = "Product value must be positive") double productValue,
            @RequestParam @Pattern(regexp = "^ACC\\d{3}$", message = "Account number must start with ACC followed by 3 digits") String customerAccountNumber) {
        // logger.info("Received request to create and save insured item:
        // productType={}, productValue={}, customerAccountNumber={}",
        // productType, productValue, customerAccountNumber);

        try {
            // Call the service method to create and save the insured item while calculating
            // the quote
            InsuredItem createdItem = insuredItemService.createAndSaveInsuredItem(
                    productType, productValue, customerAccountNumber);
            // logger.info("Successfully created and saved insured item: {}", createdItem);
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        } catch (Exception ex) {
            // logger.error("Failed to create and save insured item: {}", ex.getMessage(),
            // ex);
            return ResponseEntity.badRequest().build();
        }
    }

    /**********************************************************
     ****************** DERIVED QUERIES ***********************
     *********************************************************/

    /**************************************************************
     ********** SEARCH BY PRODUCT VALUE GREATER THAN ***************
     *************************************************************/
    // http://localhost:8888/search/greaterthan?productValue=1000
    @GetMapping("/search/greaterthan")
    public ResponseEntity<List<InsuredItem>> searchByProductValueGreaterThan(@RequestParam double productValue) {
        List<InsuredItem> results = insuredItemService.findByProductValueGreaterThan(productValue);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     ********** SEARCH BY PRODUCT VALUE LESS THAN *****************
     *************************************************************/
    // http://localhost:8888/search/lessthan?productValue=1000
    @GetMapping("/search/lessthan")
    public ResponseEntity<List<InsuredItem>> searchByProductValueLessThan(@RequestParam double productValue) {
        List<InsuredItem> results = insuredItemService.findByProductValueLessThan(productValue);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     ********** SEARCH BY PRODUCT TYPE AND PRODUCT VALUE **********
     *************************************************************/
    // http://localhost:8888/search/and?productType=laptop&productValue=800
    @GetMapping("/search/and")
    public ResponseEntity<List<InsuredItem>> searchByProductTypeAndProductValue(@RequestParam String productType,
            @RequestParam double productValue) {
        List<InsuredItem> results = insuredItemService.findByProductTypeAndProductValue(productType, productValue);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     ********** SEARCH BY PRODUCT TYPE OR PRODUCT VALUE ***********
     *************************************************************/
    // http://localhost:8888/search/or?productType=Game Console&productValue=1500
    @GetMapping("/search/or")
    public ResponseEntity<List<InsuredItem>> searchByProductTypeOrProductValue(@RequestParam String productType,
            @RequestParam double productValue) {
        List<InsuredItem> results = insuredItemService.findByProductTypeOrProductValue(productType, productValue);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     ********** SEARCH BY PRODUCT VALUE BETWEEN ******************
     *************************************************************/
    // http://localhost:8888/search/between?startValue=1000&endValue=2000
    @GetMapping("/search/between")
    public ResponseEntity<List<InsuredItem>> searchByProductValueBetween(@RequestParam double startValue,
            @RequestParam double endValue) {
        List<InsuredItem> results = insuredItemService.findByProductValueBetween(startValue, endValue);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     ********** SEARCH BY PRODUCT TYPE LIKE ************************
     *************************************************************/
    // http://localhost:8888/search/like?pattern=laptop
    @GetMapping("/search/like")
    public ResponseEntity<List<InsuredItem>> searchByProductTypeLike(@RequestParam String pattern) {
        List<InsuredItem> results = insuredItemService.findByProductTypeLikeIgnoreCase(pattern);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     ********** SEARCH BY PRODUCT TYPE IN *************************
     *************************************************************/
    // http://localhost:8888/search/in?productTypes=Laptop&productTypes=Tablet
    @GetMapping("/search/in")
    public ResponseEntity<List<InsuredItem>> searchByProductTypeIn(@RequestParam List<String> productTypes) {
        List<InsuredItem> results = insuredItemService.findByProductTypeIn(productTypes);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     ********** SEARCH BY PRODUCT TYPE ORDER BY PRODUCT VALUE DESC *
     *************************************************************/
    // http://localhost:8888/search/orderby?productType=Laptop
    @GetMapping("/search/orderby")
    public ResponseEntity<List<InsuredItem>> searchByProductTypeOrderByProductValueDesc(
            @RequestParam String productType) {
        List<InsuredItem> results = insuredItemService.findByProductTypeOrderByProductValueDesc(productType);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**************************************************************
     **************** SEARCH BY EXACT PRODUCT VALUE ***************
     *************************************************************/
    // http://localhost:8888/searchproductvalue?productValue=1000
    @GetMapping("/searchproductvalue")
    public ResponseEntity<List<InsuredItem>> searchInsuredItemByProductValue(
            @RequestParam(value = "productValue") double productValue) {
        List<InsuredItem> results = insuredItemService.findByProductValue(productValue);
        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    // http://localhost:8888/customer/customerdetailswithinsureditems
    @GetMapping("/customer/customerdetailswithinsureditems")
    public List<CustomerWithInsuredItemsDTO> getAllCustomerDetailsWithInsuredItems() {
        return insuredItemService.getAllCustomersWithInsuredItems();
    }

    // http://localhost:8888/items/insureditemswithcustomerandproductdetails
    @GetMapping("/items/insureditemswithcustomerandproductdetails")
    public List<InsuredItemWithCustomerAndProductDTO> getAllInsuredItemsWithCustomerAndProductDetails() {
        return insuredItemService.getAllInsuredItemsWithCustomerAndProductDetails();
    }

    /**************************************************************
     *********** READ ALL ITEMS AND PAGINATE 5 PER PAGE ***********
     *************************************************************/
    /*
     * This method retrieves a paginated list of insured items. The
     * 
     * @GetMapping annotation maps HTTP GET requests to the
     * findAllInsuredItem method. The @RequestParam annotations are
     * used to extract the page number and page size from the query
     * parameters of the request. If the parameters are not provided,
     * default values of 0 for page and 5 for size are used. The method
     * calls the insuredItemService to fetch the paginated data and
     * returns it as a Page object.
     */
    @GetMapping("/paginated")
    public Page<InsuredItem> findAllInsuredItemsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return insuredItemService.findAllInsuredItemsWithPagination(page, size);
    }

    /***********************************************************
     ************ READ ALL ITEMS AND PAGINATE 5 PER PAGE ********
     ************** AND SORT BY THE USER INPUT ****************
     **********************************************************
     */
    // http://localhost:8888/paginatedandsorted?sort=productType,desc&sort=quoteAmount,asc
    @GetMapping("/paginatedandsorted")
    public Page<InsuredItem> findAllInsuredItemsWithPaginationSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "sort", required = false) List<String> sort) {
        return insuredItemService.findAllInsuredItemsWithPaginationAndSort(page, size, sort);
    }

    // http://localhost:8888/insureditems/search?productType=laptop&customerAccountNumber=ACC123&productValue=900.00
    @GetMapping("/insureditems/search")
    public ResponseEntity<List<InsuredItem>> searchInsuredItems(
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String customerAccountNumber,
            @RequestParam(required = false) Double productValue) {
        List<InsuredItem> results = insuredItemService.searchInsuredItems(productType, customerAccountNumber,
                productValue);
        return ResponseEntity.ok(results);
    }

    // http://localhost:8888/searchByValueAndType?minValue=800&maxValue=1000&typePattern=Laptop
    @GetMapping("/searchByValueAndType")
    public List<InsuredItem> searchByValueAndType(
            @RequestParam double minValue,
            @RequestParam double maxValue,
            @RequestParam String typePattern) {
        return insuredItemService.findByProductValueRangeAndTypePattern(minValue, maxValue, typePattern);
    }
}