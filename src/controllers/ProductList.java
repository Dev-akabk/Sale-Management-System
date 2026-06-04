package controllers;

import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import java.util.ArrayList;
import java.util.List;
import entities.Product;

public class ProductList {

    private final List<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
            System.out.println("Product added successfully.\n");

        } else {
            System.out.println("Cannot add a null.\n");
        }
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("The product list is empty.");
            return;

        }
        for (Product p : products) {
            System.out.println(p.toString());

        }

    }

    public Product findProductById(String productId) {
        for (Product p : products) {
            if (p.getProductId().equalsIgnoreCase(productId)) {
                return p;
            }
        }
        return null;
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

    public boolean removeProductById(String productId) {
        Product foundProduct = findProductById(productId);
        if (foundProduct != null) {
            return products.remove(foundProduct);

        }
        return false;
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
