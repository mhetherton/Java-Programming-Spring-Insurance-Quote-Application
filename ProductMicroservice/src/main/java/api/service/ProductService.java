package api.service;

import api.exceptions.DuplicateProductException;
import api.exceptions.ProductNotFoundException;
import api.model.Product;
import api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The @Service annotation marks this class as a service bean
 * in the Spring application context. It is used to separate
 * business logic from the controller layer.
 */
@Service
public class ProductService {
    // Using constructor-based dependency injection
    private final ProductRepository productRepository;

    /*
     * Constructor for ProductService.
     * Injects the ProductRepository dependency.
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*
     * Retrieves all Product records from the repository.
     * Returns a list of all products.
     */
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /*
     * Adds a new Product record.
     * Checks for duplicates by product type.
     * Throws DuplicateProductException if a duplicate exists.
     * Saves and returns the new product.
     */
    public Product addProduct(Product product) {
        List<Product> existing = productRepository.findByProductTypeContainingIgnoreCase(product.getProductType());
        if (!existing.isEmpty()) {
            throw new DuplicateProductException("Product type already exists: " + product.getProductType());
        }
        return productRepository.save(product);
    }

    /*
     * Updates an existing Product by ID.
     * Validates product type is not null.
     * Updates product type and description.
     * Saves and returns the updated product.
     * Throws Exception if validation fails.
     */
    public Product updateProduct(Long id,
            Product product) throws Exception {
        if (product.getProductType() == null) {
            throw new Exception("Invalid Product: type must not be null");
        }
        Product existingProduct = findProductId(id);
        existingProduct.setProductType(product.getProductType());
        existingProduct.setProductDescription(product.getProductDescription());
        return productRepository.save(existingProduct);
    }

    /*
     * Deletes a Product record by ID.
     * Calls the repository to remove the product.
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /*
     * Retrieves a Product record by ID.
     * Throws ProductNotFoundException if not found.
     * Returns the found product.
     */
    public Product findProductId(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    /*
     * Finds products by productType (case-insensitive, contains).
     * Throws ProductNotFoundException if none found.
     * Returns a list of matching products.
     */
    public List<Product> findByProductType(String productType) {
        List<Product> results = productRepository.findByProductTypeContainingIgnoreCase(productType);
        if (results.isEmpty()) {
            throw new ProductNotFoundException("Product not found with productType: " + productType);
        }
        return results;
    }

    /*
     * Finds products by productType using a SQL LIKE pattern.
     * Automatically wraps the pattern with % for partial match.
     * Throws ProductNotFoundException if none found.
     * Returns a list of matching products.
     */
    public List<Product> findByProductTypeLikeIgnoreCase(String pattern) {
        String sqlPattern = "%" + pattern + "%";
        List<Product> results = productRepository.findByProductTypeLikeIgnoreCase(sqlPattern);
        if (results.isEmpty()) {
            throw new ProductNotFoundException("Product not found with productType like: " + pattern);
        }
        return results;
    }

    /*
     * Finds products by a list of productTypes.
     * Throws ProductNotFoundException if none found.
     * Returns a list of matching products.
     */
    public List<Product> findByProductTypeIn(List<String> productTypes) {
        List<Product> results = productRepository.findByProductTypeIn(productTypes);
        if (results.isEmpty()) {
            throw new ProductNotFoundException("Product not found with productTypes in: " + productTypes);
        }
        return results;
    }

    /*
     * Retrieves all distinct product types from the products.
     * Returns a list of unique product types.
     */
    public List<String> findAllProductTypes() {
        return findAllProducts()
                .stream()
                .map(Product::getProductType)
                .distinct()
                .toList();
    }

    /*
     * Counts the number of products with the exact productType.
     * Returns the count as a long.
     */
    public long countByProductType(String productType) {
        return findAllProducts()
                .stream()
                .filter(pd -> pd.getProductType().equalsIgnoreCase(productType))
                .count();
    }

    /*
     * Counts the total number of products.
     * Returns a string message with the count.
     */
    public String countProducts() {
        int count = findAllProducts().size();
        String message = "The number of products is " + count;
        return message;
    }
}