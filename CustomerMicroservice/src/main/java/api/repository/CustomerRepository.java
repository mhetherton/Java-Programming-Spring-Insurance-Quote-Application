package api.repository;

import api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 This interface extends the JpaRepository interface.
 The JpaRepository interface is a JPA specific repository interface
 that contains methods for performing CRUD operations on the
 Customer entity. The JpaRepository interface takes two parameters:
 1. The entity type (Customer)
 2. The type of the primary key of the entity (Long)
 The Customer interface therefore provides methods for
 performing CRUD operations such as save(), findById(), findAll(),
 deleteById(), etc.
 The Customer interface is annotated with the @Repository
 annotation. The @Repository annotation is a Spring annotation that
 indicates that the class is a repository. A repository is a mechanism
 for encapsulating storage, retrieval, and search behavior which
 emulates a collection of objects.
*/
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccountNumber(String accountNumber);

    void deleteByAccountNumber(String accountNumber);
}