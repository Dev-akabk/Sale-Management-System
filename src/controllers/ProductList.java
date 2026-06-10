package controllers;

import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import java.util.ArrayList;
import java.util.List;
import models.Product;

public class ProductList {

    private final List<Product> products;

    public ProductList() {
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

    public Product findProductById(String productId) throws InvalidInputException {
        if (productId == null || productId.trim().isEmpty()) {
            throw new InvalidInputException("Invalid product ID!");
        }
        for (Product p : products) {
            if (p.getProductId().equalsIgnoreCase(productId)) {
                return p;
            }
        }
        //return null vi neu return exception thi cac method khac se khong thuc thi duoc,
        //neu return null thi cac method khac van thuc thi duoc va chi can xu ly null o do la duoc
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
