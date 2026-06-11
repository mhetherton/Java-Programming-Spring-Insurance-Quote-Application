package api.repository;

/*
The JpaRepository interface provides various methods for performing
CRUD (Create, Read, Update, Delete) operations and pagination on the
InsuredItem entity.
*/
import api.model.InsuredItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
The InsuredItemRepository interface extends the JpaRepository interface,
which takes the entity type (InsuredItem) and the type of the primary
key (Long) as type arguments. We use this JpaRepository interface to
interact with the InsuredItem entity in the database. The interactions we
can use include the CRUD operations, sorting, and pagination.
The JpaRepository interface extends the PagingAndSortingRepository
interface, which in turn extends the CrudRepository interface.
*/

public interface InsuredItemRepository extends JpaRepository<InsuredItem, Long> {
        /* We use List to return a list of InsuredItem objects. */
        List<InsuredItem> findByCustomerAccountNumber(String customerAccountNumber);

        // Custom Queries
        // Using the equal operator to find objects
        List<InsuredItem> findByProductValue(double productValue);

        List<InsuredItem> findByProductValueGreaterThan(double productValue);

        // Using the less than operator to find objects
        List<InsuredItem> findByProductValueLessThan(double productValue);

        // Using the and operator to find objects using the equal operator
        List<InsuredItem> findByProductTypeAndProductValue(String productType,
                        double productValue);

        // Using the or operator to find objects using the equal operator
        List<InsuredItem> findByProductTypeOrProductValue(String productType,
                        double productValue);

        // Using the between operator to find objects
        List<InsuredItem> findByProductValueBetween(double startValue,
                        double endValue);

        // Using the like operator to find objects using the like operator
        List<InsuredItem> findByProductTypeLikeIgnoreCase(String pattern);

        // Using the in operator to find objects in a given List
        List<InsuredItem> findByProductTypeIn(List<String> productTypes);

        // Using the order by operator to find objects in descending order
        List<InsuredItem> findByProductTypeOrderByProductValueDesc(String productType);

        // Using a custom query to find objects based on multiple optional parameters
        @Query("SELECT i FROM InsuredItem i " +
                        "WHERE (:productType IS NULL OR i.productType = :productType) " +
                        "AND (:customerAccountNumber IS NULL OR i.customerAccountNumber = :customerAccountNumber) " +
                        "AND (:productValue IS NULL OR i.productValue = :productValue)")
        List<InsuredItem> findByProductTypeAndCustomerAccountNumberAndProductValue(
                        @Param("productType") String productType,
                        @Param("customerAccountNumber") String customerAccountNumber,
                        @Param("productValue") Double productValue);

        // Finds insured items with productValue between two values and productType
        // matching a pattern
        @Query("SELECT i FROM InsuredItem i " +
                        "WHERE i.productValue BETWEEN :minValue AND :maxValue " +
                        "AND LOWER(i.productType) LIKE LOWER(CONCAT('%', :typePattern, '%'))")
        List<InsuredItem> findByProductValueRangeAndTypePattern(
                        @Param("minValue") double minValue,
                        @Param("maxValue") double maxValue,
                        @Param("typePattern") String typePattern);

}