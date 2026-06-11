package api.controller;

import api.service.quotecalculations.CalculateQuoteService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

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
public class QuoteController {
    // Create an instance of the CalculateQuote service
    private final CalculateQuoteService calculateQuote;

    // Create a logger instance for this class
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InsuredItemController.class);

    /*
     * Constructor to inject the CalculateQuote service.
     * This is a dependency injection pattern where the CalculateQuote
     * service is injected into the QuoteController class
     */
    public QuoteController(CalculateQuoteService calculateQuote) {
        this.calculateQuote = calculateQuote;
    }

    /**************************************************************
     *********************** CALCULATE QUOTE **********************
     **************************************************************/
    // Endpoint to calculate the quote for a customer
    // http://localhost:8888/quote?productType=Laptop&productValue=400
    @GetMapping("/quote")
    public double calculateQuote(@RequestParam @NotBlank(message = "Product type is required") String productType,
            @RequestParam @Positive(message = "Product value must be positive") double productValue) {
        // CalculateQuote calculateQuote = new CalculateQuote();
        logger.info("Calculating quote for productType: {}, productValue: {}", productType, productValue);
        double quote = calculateQuote.calculateQuote(productType, productValue);
        logger.info("Calculated quote: {}", quote);
        return quote;

    }

    @GetMapping("/check-threads")
    public Map<String, Object> checkThreads() {
        Thread currentThread = Thread.currentThread();
        return Map.of(
                "threadName", currentThread.toString(),
                "isVirtual", currentThread.isVirtual() // Returns true if it's a virtual thread
        );
    }

}