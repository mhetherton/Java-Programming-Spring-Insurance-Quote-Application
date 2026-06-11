package api.controller;

import api.dto.ProductRequestDTO;
import api.dto.ProductResponseDTO;
import api.exceptions.DuplicateProductException;
import api.model.Product;
import api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/product")
public class ProductController {
    // Using constructor-based dependency injection
    private final ProductService productService;

    // Injecting ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * Returns all Product records.
     * Calls the service to fetch all products.
     * Responds with HTTP 200 OK and the list of products.
     * http://localhost:9998/api/product
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts() {
        List<ProductResponseDTO> product = productService.findAllProducts()
                .stream()
                .map(pd -> new ProductResponseDTO(pd.getId(),
                        pd.getProductType(), pd.getProductDescription()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /*
     * Adds a new Product record.
     * Handles duplicate product exception.
     * Responds with HTTP 201 Created or 409 Conflict.
     * http://localhost:9998/api/product
     */
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductRequestDTO dto) {
        try {
            Product product = new Product(dto.productType(),
                    dto.productDescription());
            Product newProduct = productService.addProduct(product);
            ProductResponseDTO responseDto = new ProductResponseDTO(
                    newProduct.getId(),
                    newProduct.getProductType(),
                    newProduct.getProductDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (DuplicateProductException ex) {
            String message = "Product already exists: " + ex.getMessage();
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /*
     * Updates an existing Product record by ID.
     * Calls the service to update the product.
     * Responds with HTTP 200 OK and the updated product.
     * http://localhost:9998/api/product/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
            @RequestBody ProductRequestDTO dto) throws Exception {
        Product product = new Product(dto.productType(), dto.productDescription());
        Product updatedProduct = productService.updateProduct(id, product);
        ProductResponseDTO responseDto = new ProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getProductType(),
                updatedProduct.getProductDescription());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /*
     * Deletes a Product record by ID.
     * Calls the service to delete the product.
     * Responds with HTTP 200 OK and a deletion message.
     * http://localhost:9998/api/product/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Record deleted", HttpStatus.OK);
    }

    /*
     * Retrieves a Product record by ID.
     * Calls the service to fetch the product.
     * Responds with HTTP 200 OK and the product, or 404 Not Found.
     * Get a product by ID
     * http://localhost:9998/api/product/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id) {
        Product product = productService.findProductId(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        ProductResponseDTO responseDto = new ProductResponseDTO(
                product.getId(),
                product.getProductType(),
                product.getProductDescription());
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Searches for Product by product type.
     * Calls the service to fetch matching products.
     * Responds with HTTP 200 OK and the list of products.
     * http://localhost:9998/api/product/search/type?productType=Laptop
     */
    @GetMapping("/search/type")
    public ResponseEntity<List<ProductResponseDTO>> findByProductType(@RequestParam String productType) {
        List<ProductResponseDTO> products = productService.findByProductType(productType)
                .stream()
                .map(pd -> new ProductResponseDTO(pd.getId(),
                        pd.getProductType(), pd.getProductDescription()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /*
     * Searches for products by productType using a pattern.
     * Calls the service to find products matching the pattern.
     * Returns a list of matching Product.
     * http://localhost:9998/api/product/search/type/like?pattern=M
     */
    @GetMapping("/search/type/like")
    public ResponseEntity<List<ProductResponseDTO>> findByProductTypeLikeIgnoreCase(@RequestParam String pattern) {
        List<ProductResponseDTO> products = productService.findByProductTypeLikeIgnoreCase(pattern)
                .stream()
                .map(pd -> new ProductResponseDTO(pd.getId(), pd.getProductType(),
                        pd.getProductDescription()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /*
     * Searches for products by a list of product types.
     * Calls the service to find products matching any of the types.
     * Returns a list of matching Product.
     * http://localhost:9998/api/product/search/type/in?productTypes=Laptop,
     * Smartwatch
     */
    @GetMapping("/search/type/in")
    public ResponseEntity<List<ProductResponseDTO>> findByProductTypeIn(@RequestParam List<String> productTypes) {
        List<ProductResponseDTO> products = productService.findByProductTypeIn(productTypes)
                .stream()
                .map(pd -> new ProductResponseDTO(pd.getId(), pd.getProductType(),
                        pd.getProductDescription()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /*
     * Retrieves all distinct product types.
     * Calls the service to fetch all products and extract types.
     * Returns a list of unique product types.
     * http://localhost:9998/api/product/types
     */
    @GetMapping("/types")
    public ResponseEntity<List<String>> findAllProductTypes() {
        List<String> types = productService.findAllProductTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    /*
     * Counts products by product type.
     * Calls the service to fetch all products and filter by type.
     * Returns the count of matching products.
     * http://localhost:9998/api/product/count/type?productType=Laptop
     */
    @GetMapping("/count/type")
    public ResponseEntity<Long> countByProductType(@RequestParam String productType) {
        long count = productService.countByProductType(productType);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /*
     * Counts the total number of products.
     * Calls the service to fetch all products and counts them.
     * Returns a message with the total count.
     * http://localhost:9998/api/product/count
     */
    @GetMapping("/count")
    public ResponseEntity<String> countProducts() {
        return ResponseEntity.ok(productService.countProducts());
    }
}