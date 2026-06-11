package api.dto;

/**
 * A DTO is used to transfer data between processes. It is often used to
 * encapsulate
 * data and send it from one subsystem of an application to another.
 * In our application we will use this CustomerDTO to receive customer details
 * from a Customer microservice and then display it. It includes fields for
 * account number, name, and email, along with constructors, getters, and
 * setters.
 */
public class CustomerDTO {
    // Private fields for customer details
    private String accountNumber;
    private String name;
    private String email;

    // Default constructor
    public CustomerDTO() {
    }

    // Parameter constructor
    public CustomerDTO(String accountNumber, String name, String email) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
    }

    // Getters and setters for each private field
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "CustomerDTO [accountNumber=" + accountNumber + ", " +
                "name=" + name + ", email=" + email + "]";
    }

}