package controllers;

import entity.Product;
import java.util.ArrayList;
import java.util.List;

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
