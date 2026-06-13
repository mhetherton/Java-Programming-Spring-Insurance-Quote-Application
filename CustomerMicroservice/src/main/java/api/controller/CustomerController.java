package api.controller;

import api.dto.CustomerDTO;
import api.service.CustomerService;
import api.service.ProductClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/customer")
public class CustomerController {
    // Using constructor-based dependency injection
    private final CustomerService customerService;

    // Add this field to CustomerController
    private final ProductClientService productClientService;

    // Injecting ProductTypeService
    public CustomerController(CustomerService customerService, ProductClientService productClientService) {
        this.customerService = customerService;
        this.productClientService = productClientService;
    }

    /*
     * findAllCustomer()
     * Handles GET requests to retrieve all customer Returns a list of Customer.
     * http://localhost:9999/api/customer
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAllCustomer() {
        List<CustomerDTO> customer = customerService.findAllCustomer();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /*
     * addCustomer()
     * Handles POST requests to add new customer. Accepts a Customer object in the
     * request body and returns the created Customer object with a CREATED status.
     * http://localhost:9999/api/customer
     */
    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO newCustomer = customerService.addCustomer(customerDTO);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    /*
     * updateCustomer()
     * Handles PUT requests to update existing customer. Accepts an account number
     * as a path variable and a Customer object in the request body. Returns the
     * updated Customer object with an OK status.
     * http://localhost:9999/api/customer/ACC123
     */
    @PutMapping("/{accountNumber}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String accountNumber,
            @RequestBody CustomerDTO customerDTO) throws Exception {
        CustomerDTO updatedCustomer = customerService.updateCustomer(accountNumber, customerDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    /*
     * deleteCustomer()
     * Handles DELETE requests to remove customer by account number. Accepts an
     * account number as a path variable and returns a confirmation message with an
     * OK status.
     * http://localhost:9999/api/customer/ACC127
     */
    //
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String accountNumber) throws Exception {
        customerService.deleteCustomer(accountNumber);
        return new ResponseEntity<>("Record deleted", HttpStatus.OK);
    }

    /**
     * findCustomerByAccountNumber()
     * Handles GET requests to retrieve customer by account number. Accepts an
     * account number as a path variable and returns the corresponding Customer
     * object with an OK status. If not found, returns a NOT FOUND status.
     * http://localhost:9999/api/customer/account/ACC123
     */
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<CustomerDTO> findCustomerByAccountNumber(@PathVariable String accountNumber) {
        CustomerDTO customer = customerService.findCustomerByAccountNumber(accountNumber);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    /**
     * findCustomerById()
     * Handles GET requests to retrieve customer by ID. Accepts an ID as a path
     * variable and returns the corresponding Customer object with an OK status. If
     * not found, returns a NOT FOUND status.
     * http://localhost:9999/api/customer/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.findCustomerId(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    /*
     * countCustomers()
     * Handles GET requests to count the total number of customers. Returns the
     * count as a string message with an OK status.
     * http://localhost:9999/api/customer/count
     */
    @GetMapping("/count")
    public ResponseEntity<String> countCustomers() {
        long count = customerService.findAllCustomer().size();
        String message = "The number of customers is " + count;
        return ResponseEntity.ok(message);
    }

    /*
     * Retrieves all product types from Product microservice
     * Returns a list of product type strings
     * http://localhost:9999/api/customer/product-types
     */
    @GetMapping("/product-types")
    public ResponseEntity<List<String>> getProductTypes() {
        List<String> productTypes = productClientService.getAllProductTypes();
        return new ResponseEntity<>(productTypes, HttpStatus.OK);
    }

    /*
     * Retrieves product descriptions by type from Product microservice
     * 
     * @param productType the product type to search for Returns a list of product
     * descriptions for the specified type
     * http://localhost:9999/api/customer/products/descriptions?productType=Laptop
     */
    @GetMapping("/products/descriptions")
    public ResponseEntity<List<String>> getProductDescriptionsByType(@RequestParam String productType) {
        List<String> descriptions = productClientService.getProductDescriptionsByType(productType);
        return new ResponseEntity<>(descriptions, HttpStatus.OK);
    }
}