package controllers;

import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
        System.out.println("Product added successfully.\n");
    }
        

    public void displayAllProducts() throws InvalidInputException {
        if (products.isEmpty()) {
            throw new InvalidInputException("Product list is empty!");
        }
        for (Product p : products) {
            System.out.println(p.toString());
        }
    }
    //find(Product) for update, remove to return out
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
        
    //search for print out
    public void searchProducts(String keyword) throws InvalidInputException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidInputException("Search keyword cannot be empty!");
        }

        System.out.println("\n--- SEARCH RESULTS ---");
        boolean found = false;
        
        for (Product p : products) {
            // Search only keyword or more...both name & category can find out anf print
            if (p.getProductName().toLowerCase().contains(keyword.toLowerCase()) || 
                p.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(p.toString());
                found = true;
            }
        }
        //bien linh canh
        if (!found) {
            System.out.println("No products found matching keyword: '" + keyword + "'");
        }
        System.out.println("----------------------\n");
    }
    
    
    public void updateProduct(String productId, String newName, double newPrice) throws ItemNotFoundException, InvalidInputException {
        Product foundProduct = findProductById(productId);
        if (foundProduct == null) {
            throw new ItemNotFoundException("Product with ID: " + productId + " not found for update!");
        }
        
        if (newPrice <= 0) {
            throw new InvalidInputException("Updated product price must be greater than 0!");
        }

        foundProduct.setProductName(newName); 
        foundProduct.setPrice(newPrice);
        System.out.println("Product updated successfully.\n");
    }

    public boolean removeProductById(String productId) throws ItemNotFoundException, InvalidInputException {
        Product foundProduct = findProductById(productId);
        if (foundProduct == null) {
            throw new ItemNotFoundException("Product with ID: " + productId + " not found for removal!");
        }
        return products.remove(foundProduct);
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
