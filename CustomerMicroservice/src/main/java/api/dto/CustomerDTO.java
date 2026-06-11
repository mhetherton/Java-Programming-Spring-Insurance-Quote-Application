package api.dto;

/**
 * CustomerDTO
 * Data Transfer Object for customer.
 * Used for transferring data between client and server.
 */
public record CustomerDTO(String accountNumber, String name, String email) {
}

/*
 * 
 * public class CustomerDTO {
 * private String accountNumber;
 * private String name;
 * private String email;
 * 
 * public CustomerDTO() {
 * }
 * 
 * public CustomerDTO(String accountNumber, String name, String email) {
 * this.accountNumber = accountNumber;
 * this.name = name;
 * this.email = email;
 * }
 * 
 * public String getAccountNumber() {
 * return accountNumber;
 * }
 * 
 * public void setAccountNumber(String accountNumber) {
 * this.accountNumber = accountNumber;
 * }
 * 
 * public String getName() {
 * return name;
 * }
 * 
 * public void setName(String name) {
 * this.name = name;
 * }
 * 
 * public String getEmail() {
 * return email;
 * }
 * 
 * public void setEmail(String email) {
 * this.email = email;
 * }
 * }
 * 
 */