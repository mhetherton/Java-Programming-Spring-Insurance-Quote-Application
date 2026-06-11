package api.exceptions;

/**
 * Exception thrown when a customer is invalid.
 */
public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException(String message) {
        // Call the constructor of the superclass (RuntimeException)
        super(message);
    }
}