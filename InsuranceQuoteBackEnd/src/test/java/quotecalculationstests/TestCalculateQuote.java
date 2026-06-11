package quotecalculationstests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import api.service.quotecalculations.CalculateQuoteService;

import static org.junit.jupiter.api.Assertions.*;

public class TestCalculateQuote {
    /*
     * Declare an instance of CalculateQuote, an object that will be used
     * to test the methods in the CalculateQuote class.This instance will be
     * used in each test method to call the methods being tested. This is done to
     * avoid creating a new instance in each test method, which would be redundant
     * and inefficient. Instead, we create a single instance that can be reused
     * across all tests. This is a common practice in unit testing to improve
     * performance and maintainability of the test code.
     */
    private CalculateQuoteService calculateQuote;

    /*
     * The setUp() method is annotated with @BeforeEach, which means it will
     * be executed before each test method in this class.
     * The method initializes the quoteCalculations instance before each test,
     * ensuring that each test starts with a fresh instance of QuoteCalculations.
     * This is important to ensure that tests do not interfere with each other,
     * as each test should be independent and not rely on the state of the
     * QuoteCalculations instance from previous tests.
     */
    @BeforeEach
    public void setUp() {
        calculateQuote = new CalculateQuoteService();
    }

    @Test
    public void calculateQuoteMobileAndLessThanOrEqualTo500() {
        assertEquals(4.4, calculateQuote.calculateQuote("Mobile Phone", 400), 0.0);
    }

    @Test
    public void calculateQuoteMobileAndGreaterThan500() {
        assertEquals(13.2, calculateQuote.calculateQuote("Mobile Phone", 1000), 0.0);
    }

    @Test
    public void calculateQuoteForNegativeValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculateQuote.calculateQuote("Mobile Phone", -100));
    }

    @Test
    public void calculateQuoteInvalidProductType() {
        assertEquals(1.4, calculateQuote.calculateQuote("InvalidType", 100), 0.0);
    }

    @Test
    public void calculateQuoteZeroProductValue() {
        assertThrows(IllegalArgumentException.class, () -> calculateQuote.calculateQuote("Mobile Phone", 0));
    }

    @Test
    public void calculateQuoteNullProductType() {

        assertThrows(NullPointerException.class, () -> calculateQuote.calculateQuote(null, 100));
    }

    @Test
    public void calculateQuoteUpperBoundaryValue() {
        double result = calculateQuote.calculateQuote("Mobile Phone",
                Double.MAX_VALUE);
        assertFalse(Double.isFinite(result), "Result should be a " +
                "finite number");
    }

    @Test
    public void calculateQuoteValidProductTypes() {
        assertEquals(4.8, calculateQuote.calculateQuote("Laptop", 400), 0.0);
        assertEquals(5.2, calculateQuote.calculateQuote("Television", 400), 0.0);
    }

}