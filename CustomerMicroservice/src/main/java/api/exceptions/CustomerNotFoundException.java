package api.exceptions;

/**
 * Exception thrown when a customer is not found.
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        // Call the constructor of the superclass (RuntimeException)
        super(message);
    }
}