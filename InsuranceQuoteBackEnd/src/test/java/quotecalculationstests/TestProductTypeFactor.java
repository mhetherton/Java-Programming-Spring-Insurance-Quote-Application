package quotecalculationstests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import api.service.quotecalculations.ProductTypeFactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProductTypeFactor {
    /*
     * Declare an instance of ProductTypeFactor, an object that will be used
     * to test the methods in the ProductTypeFactor class.This instance will be
     * used in each test method to call the methods being tested. This is done to
     * avoid creating a new instance in each test method, which would be redundant
     * and inefficient. Instead, we create a single instance that can be reused
     * across all tests. This is a common practice in unit testing to improve
     * performance and maintainability of the test code.
     */
    private ProductTypeFactor productTypeFactor;

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
        productTypeFactor = new ProductTypeFactor();
    }

    @Test
    public void productTypeFactorMobile() {
        assertEquals(1.1, productTypeFactor.productTypeFactor("Mobile Phone"), 0.00);
    }

    @Test
    public void productTypeFactorLaptop() {
        assertEquals(1.2, productTypeFactor.productTypeFactor("Laptop"), 0.00);
    }

    @Test
    public void productTypeFactorTelevision() {
        assertEquals(1.3, productTypeFactor.productTypeFactor("Television"), 0.00);
    }

    @Test
    public void productTypeFactorOther() {
        assertEquals(1.4, productTypeFactor.productTypeFactor("Charger"), 0.00);
    }

    @Test
    public void productTypeFactorShouldReturnDefaultForUnknownType() {
        assertEquals(1.4, productTypeFactor.productTypeFactor("UnknownType"), 0.00);
    }
}