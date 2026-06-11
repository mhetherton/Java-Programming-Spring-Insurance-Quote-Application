package application;

import java.lang.reflect.Method;

import api.service.quotecalculations.CalculateQuoteService;

public class QuoteApplication {

    public static void main(String[] args) {

        /*
         * Create an instance of the CalculateQuote class.
         * This class contains the calculateQuote() method
         * that calculates the quote based on the product name and price.
         */
        CalculateQuoteService calculateQuoteService = new CalculateQuoteService();

        // Call the calculateQuote() method and pass the name and price
        // In our tests we showed the quote for this product was 4.4
        System.out.println(calculateQuoteService.calculateQuote(
                "Mobile Phone", 400.00));

        // Call the calculateQuote() method and pass the name and price
        // In our tests we showed the quote for this product was 4.8
        System.out.println(calculateQuoteService.calculateQuote("Laptop", 400));

        // Call the calculateQuote() method and pass the name and price
        // In our tests we showed the quote for this product was 5.2
        System.out.println(calculateQuoteService.calculateQuote("Television", 400));
    }

}