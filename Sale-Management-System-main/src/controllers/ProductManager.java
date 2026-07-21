package controllers;

import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import models.Product;

public class ProductManager {
    private final List<Product> products;

    public ProductManager() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) throws InvalidInputException {
        if (product == null) {
            throw new InvalidInputException("Cannot add a null product!");
        }
        this.products.add(product);
        // Success messaging delegated to view layer
    }

    // find(Product) for update, remove to return out
    public Product findProductById(String productId) throws InvalidInputException {
        //Validate
        if (productId == null || productId.trim().isEmpty()) {
            throw new InvalidInputException("Invalid product ID!");
        }
        
        // Search by Stream API
        // Loc danh sach ko phan biet hoa thuong, ko co thi tra ve null
        return products.stream()
                .filter(p -> p.getProductId().equalsIgnoreCase(productId.trim()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Stream-based keyword search over product name and category.
     * Returns a filtered list for the view layer to display.
     *
     * @param keyword  search term (matched case-insensitively against name and category)
     * @return list of matching products (empty list if none found)
     * @throws InvalidInputException if keyword is null or blank
     */
    public List<Product> searchProducts(String keyword) throws InvalidInputException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidInputException("Search keyword cannot be empty!");
        }
        String lowerKey = keyword.toLowerCase().trim();
        return products.stream()
                .filter(p -> p.getProductName().toLowerCase().contains(lowerKey)
                          || p.getCategory().toLowerCase().contains(lowerKey))
                .collect(Collectors.toList());
    }

    public void updateProduct(String productId, String newName, double newPrice)
            throws ItemNotFoundException, InvalidInputException {
        Product foundProduct = findProductById(productId);
        if (foundProduct == null) {
            throw new ItemNotFoundException("Product with ID: " + productId + " not found for update!");
        }

        if (newPrice <= 0) {
            throw new InvalidInputException("Updated product price must be greater than 0!");
        }

        foundProduct.setProductName(newName);
        foundProduct.setPrice(newPrice);
        // Success messaging delegated to view layer
    }

    public boolean removeProductById(String productId) throws ItemNotFoundException, InvalidInputException {
        Product foundProduct = findProductById(productId);
        if (foundProduct == null) {
            throw new ItemNotFoundException("Product with ID: " + productId + " not found for removal!");
        }
        return products.remove(foundProduct);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }
}
