package application;

import api.service.quotecalculations.CalculateQuote;

public class QuoteApplication {

    public static void main(String[] args) {

        try {
            java.lang.reflect.Method method = Thread.class.getMethod("isVirtual");
            System.out.println("Is virtual thread: " + method.invoke(Thread.currentThread()));
        } catch (Exception e) {
            System.out.println("Virtual threads are not supported in this Java version.");
        }

        /*
         * Create an instance of the CalculateQuote class.
         * This class contains the calculateQuote() method
         * that calculates the quote based on the product name and price.
         */
        CalculateQuote myCalculateQuote = new CalculateQuote();

        // Call the calculateQuote() method and pass the name and price
        // In our tests we showed the quote for this product was 4.4
        System.out.println(myCalculateQuote.calculateQuote(
                "Mobile Phone", 400.00));

        // Call the calculateQuote() method and pass the name and price
        // In our tests we showed the quote for this product was 4.8
        System.out.println(myCalculateQuote.calculateQuote("Laptop", 400));

        // Call the calculateQuote() method and pass the name and price
        // In our tests we showed the quote for this product was 5.2
        System.out.println(myCalculateQuote.calculateQuote("Television", 400));
    }

}