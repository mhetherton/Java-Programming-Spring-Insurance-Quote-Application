package api.exceptions;

import api.service.ExternalServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExternalServiceClient.class);

    /*
     * This method handles the MissingServletRequestParameterException
     * which is thrown when a required request parameter is missing.
     * It returns a ResponseEntity with a BAD_REQUEST status and a
     * message indicating which parameter is missing.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String message = ex.getParameterName() + " is required";
        // Log the missing parameter
        logger.warn("Missing request parameter: {}", ex.getParameterName());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /*
     * This method handles the MethodArgumentNotValidException which is
     * thrown when a method argument fails validation. It returns a
     * ResponseEntity with a BAD_REQUEST status and a message containing
     * all validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String messages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        // Log the validation errors
        logger.warn("Validation failed: {}", messages);

        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }

    /*
     * This method handles the InvalidQuoteException and QuoteNotFoundException
     * which are custom exceptions defined in the application. It returns a
     * ResponseEntity with appropriate HTTP status codes and messages based
     * on the type of exception.
     */
    @ExceptionHandler(InvalidQuoteException.class)
    public ResponseEntity<String> handleInvalidQuote(InvalidQuoteException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*
     * This method handles the QuoteNotFoundException which is thrown
     * when a requested quote is not found. It returns a ResponseEntity
     * with a NOT_FOUND status and a message indicating that the quote
     * was not found.
     */
    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<String> handleQuoteNotFound(QuoteNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}