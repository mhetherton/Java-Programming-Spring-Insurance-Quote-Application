package quotecalculationstests;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import api.service.quotecalculations.ProductValueFactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestProductValueFactor {
    /*
     * Declare an instance of ProductValueFactor, an object that will be used
     * to test the methods in the ProductValueFactor class.This instance will be
     * used in each test method to call the methods being tested. This is done to
     * avoid creating a new instance in each test method, which would be redundant
     * and inefficient. Instead, we create a single instance that can be reused
     * across all tests. This is a common practice in unit testing to improve
     * performance and maintainability of the test code.
     */
    private ProductValueFactor productValueFactor;

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
        productValueFactor = new ProductValueFactor();
    }

    @Test
    public void productValueFactorLessThanOrEqualTo500() {
        assertEquals(1.0, productValueFactor.productValueFactor(400), 0.00);
    }

    @Test
    public void productValueFactorGreaterThan500() {
        assertEquals(1.2, productValueFactor.productValueFactor(600), 0.00);
    }

    @Test
    public void productValueFactorLessThanZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> productValueFactor.productValueFactor(-100));
    }

    @Test
    public void productValueFactorExactly500() {
        assertEquals(1.0, productValueFactor.productValueFactor(500), 0.00);
    }

    @Test
    public void productValueFactorJustAbove500() {
        assertEquals(1.2, productValueFactor.productValueFactor(501), 0.00);
    }

    @Test
    public void productValueFactorZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> productValueFactor.productValueFactor(0));
    }

}