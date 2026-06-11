package quotecalculationstests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import api.service.quotecalculations.QuoteCalculations;

import static org.junit.jupiter.api.Assertions.*;

public class TestFactors {
    /*
     * Declare an instance of QuoteCalculations, an object that will be used
     * to test the methods in the QuoteCalculations class.This instance will be
     * used in each test method to call the methods being tested. This is done to
     * avoid creating a new instance in each test method, which would be redundant
     * and inefficient. Instead, we create a single instance that can be reused
     * across all tests. This is a common practice in unit testing to improve
     * performance and maintainability of the test code.
     */
    private QuoteCalculations quoteCalculations;

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
        quoteCalculations = new QuoteCalculations();
    }

    @Test
    public void productTypeFactorMobile() {

        assertEquals(1.1, quoteCalculations.productTypeFactor("Mobile Phone"), 0.00);
    }

    @Test
    public void productTypeFactorLaptop() {

        assertEquals(1.2, quoteCalculations.productTypeFactor("Laptop"), 0.00);
    }

    @Test
    public void productTypeFactorTelevision() {

        assertEquals(1.3, quoteCalculations.productTypeFactor("Television"), 0.00);
    }

    @Test
    public void productTypeFactorOther() {

        assertEquals(1.4, quoteCalculations.productTypeFactor("Charger"), 0.00);
    }

    @Test
    public void productTypeFactorShouldReturnDefaultForUnknownType() {

        assertEquals(1.4, quoteCalculations.productTypeFactor("UnknownType"), 0.00);
    }

    @Test
    public void productValueFactorLessThanOrEqualTo500() {

        assertEquals(1.0, quoteCalculations.productValueFactor(400), 0.00);
    }

    @Test
    public void productValueFactorGreaterThan500() {

        assertEquals(1.2, quoteCalculations.productValueFactor(600), 0.00);
    }

    @Test
    public void productValueFactorLessThanZeroThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> quoteCalculations.productValueFactor(-100));
    }

    @Test
    public void productValueFactorExactly500() {

        assertEquals(1.0, quoteCalculations.productValueFactor(500), 0.00);
    }

    @Test
    public void productValueFactorJustAbove500() {

        assertEquals(1.2, quoteCalculations.productValueFactor(501), 0.00);
    }

    @Test
    public void productValueFactorZeroThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> quoteCalculations.productValueFactor(0));
    }

    @Test
    public void calculateQuoteMobileAndLessThanOrEqualTo500() {

        assertEquals(4.4, quoteCalculations.calculateQuote("Mobile Phone", 400), 0.0);
    }

    @Test
    public void calculateQuoteMobileAndGreaterThan500() {

        assertEquals(13.2, quoteCalculations.calculateQuote("Mobile Phone", 1000), 0.0);
    }

    @Test
    public void calculateQuoteForNegativeValueThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> quoteCalculations.calculateQuote("Mobile Phone", -100));
    }

    @Test
    public void calculateQuoteInvalidProductType() {
        assertEquals(1.4, quoteCalculations.calculateQuote("InvalidType", 100), 0.0);
    }

    @Test
    public void calculateQuoteZeroProductValue() {
        assertThrows(IllegalArgumentException.class, () -> quoteCalculations.calculateQuote("Mobile Phone", 0));
    }

    @Test
    public void calculateQuoteNullProductType() {

        assertThrows(NullPointerException.class, () -> quoteCalculations.calculateQuote(null, 100));
    }

    @Test
    public void calculateQuoteUpperBoundaryValue() {
        double result = quoteCalculations.calculateQuote("Mobile Phone",
                Double.MAX_VALUE);
        assertFalse(Double.isFinite(result), "Result should be a " +
                "finite number");
    }

    @Test
    public void calculateQuoteValidProductTypes() {
        assertEquals(4.8, quoteCalculations.calculateQuote("Laptop", 400), 0.0);
        assertEquals(5.2, quoteCalculations.calculateQuote("Television", 400), 0.0);
    }

}