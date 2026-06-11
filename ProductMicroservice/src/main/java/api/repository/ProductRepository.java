package api.repository;

import api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface extends the JpaRepository interface.
 * The JpaRepository interface is a JPA specific repository interface
 * that contains methods for performing CRUD operations on the
 * Product entity. The JpaRepository interface takes two parameters:
 * 1. The entity type (Product)
 * 2. The type of the primary key of the entity (Long)
 * The ProductRepository interface therefore provides methods for
 * performing CRUD operations such as save(), findById(), findAll(),
 * deleteById(), etc.
 * The ProductRepository interface is annotated with the @Repository
 * annotation. The @Repository annotation is a Spring annotation that
 * indicates that the class is a repository. A repository is a mechanism
 * for encapsulating storage, retrieval, and search behavior which
 * emulates a collection of objects.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /***************************************************************
     * Finds products where productType contains the given value, *
     * ignoring case. Returns a list of matching Product. *
     ***************************************************************/
    List<Product> findByProductTypeContainingIgnoreCase(String productType);

    /***************************************************************
     * Finds products where productType matches the given SQL *
     * LIKE pattern, ignoring case. Returns a list of matches. *
     ***************************************************************/
    List<Product> findByProductTypeLikeIgnoreCase(String pattern);

    /***************************************************************
     * Finds products where productType is in the given list. *
     * Returns a list of matching Product. *
     ***************************************************************/
    List<Product> findByProductTypeIn(List<String> productTypes);

} // End of ProductRepository interface