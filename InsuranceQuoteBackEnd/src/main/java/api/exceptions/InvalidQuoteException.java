package api.exceptions;

/**
 * This exception is a subclass of RuntimeException, which is an
 * unchecked exception in Java. Unchecked exceptions are exceptions that
 * do not need to be declared in a method's or constructor's throws
 * clause. This InvalidQuoteException class has a constructor that
 * accepts a String argument, which is the message of the exception.
 * This message is passed to the superclass constructor using the
 * super(message) call. This message can be used to provide detailed
 * information about the exception, such as what operation was being
 * performed when the exception occurred.
 */
public class InvalidQuoteException extends RuntimeException {
    // Constructor that accepts a message
    public InvalidQuoteException(String message) {
        // Pass the message to the superclass constructor
        super(message);
    }

}