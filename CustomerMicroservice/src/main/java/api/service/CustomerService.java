package api.service;

import api.dto.CustomerDTO;
import api.exceptions.CustomerNotFoundException;
import api.model.Customer;
import api.repository.CustomerRepository;
import api.exceptions.InvalidCustomerException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The @Service annotation is used to mark the class as a service bean
 * in the Spring application context. The service bean is used to write
 * business logic in a different layer, separated from the controller
 * layer.
 */
@Service
public class CustomerService {
    // Using constructor-based dependency injection
    private final CustomerRepository customerRepository;

    // Injecting CustomerService object
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * findAllCustomer()
     * Retrieves all customer from the repository.
     * Returns a list of CustomerDTO.
     */
    @Cacheable(value = "customers", key = "'all'")
    public List<CustomerDTO> findAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * addCustomer()
     * Adds a new customer to the repository.
     * Accepts a CustomerDTO object.
     * Returns the saved CustomerDTO.
     */
    @CacheEvict(value = "customers", allEntries = true)
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer entity = toEntity(customerDTO);
        Customer saved = customerRepository.save(entity);
        return toDto(saved);
    }

    /**
     * updateCustomer()
     * Updates an existing customer's by account number.
     * Accepts accountNumber and CustomerDTO object.
     * Returns the updated CustomerDTO.
     * Throws exception if account number is changed or name is null.
     */
    @CacheEvict(value = "customers", allEntries = true)
    public CustomerDTO updateCustomer(String accountNumber,
            CustomerDTO customerDTO) throws Exception {
        Customer existing = findCustomerByAccountNumberEntity(accountNumber);

        if (!customerDTO.accountNumber().equals(existing.getAccountNumber())) {
            throw new InvalidCustomerException("Invalid - Cannot change account number");
        }

        if (customerDTO.name() == null) {
            throw new InvalidCustomerException("Invalid customer : name must not be null");
        }

        existing.setName(customerDTO.name());
        existing.setEmail(customerDTO.email());
        Customer updated = customerRepository.save(existing);
        return toDto(updated);
    }

    /**
     * Method to delete a customer. We use the @Transactional annotation
     * to ensure the delete operation is performed within a transaction.
     * We use the @Transactional annotation because the
     * deleteByAccountNumber() method has been defined in the
     * CustomerRepository interface, it is not a built-in
     * method of the JpaRepository interface.
     */
    @Transactional
    @CacheEvict(value = "customers", allEntries = true)
    public void deleteCustomer(String accountNumber) throws Exception {
        customerRepository.deleteByAccountNumber(accountNumber);
    }

    /**
     * findCustomerId()
     * Retrieves customer by ID.
     * Returns the CustomerDTO if found, otherwise throws.
     */
    @Cacheable(value = "customers", key = "#id")
    public CustomerDTO findCustomerId(Long id) {
        Customer entity = customerRepository.findById(id).orElseThrow();
        return toDto(entity);
    }

    /**
     * findCustomerByAccountNumber()
     * Retrieves customer by account number.
     * Returns the CustomerDTO if found, otherwise throws.
     */
    @Cacheable(value = "customers", key = "#accountNumber")
    public CustomerDTO findCustomerByAccountNumber(String accountNumber) {
        Customer entity = findCustomerByAccountNumberEntity(accountNumber);
        return toDto(entity);
    }

    // Internal helper to get entity by account number
    private Customer findCustomerByAccountNumberEntity(String accountNumber) {
        return customerRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with account number: " + accountNumber));
    }

    /*
     * toDto() - Maps Customer entity to CustomerDTO.
     */
    private CustomerDTO toDto(Customer entity) {
        return new CustomerDTO(entity.getAccountNumber(), entity.getName(), entity.getEmail());
    }

    /*
     * toEntity() - Maps CustomerDTO to Customer entity.
     */
    private Customer toEntity(CustomerDTO dto) {
        return new Customer(dto.accountNumber(), dto.name(), dto.email());
    }

}