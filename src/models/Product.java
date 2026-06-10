package models;

import utils.Validation;
import exceptions.InvalidInputException;


public class Product {

    private String productId;
    private String productName;
    private String category;
    private double price;
    private int stockQuantity;

    // Constructor-------
    public Product(String productId, String productName, String category, double price, int stockQuantity)
             throws InvalidInputException {
        //validate input data

        // Validation.checkNonNegativeDouble(price, "Product price");
        // Validation.checkNonNegativeInt(stockQuantity, "Stock quantity");
        Validation.checkProductIdFormat(productId);
        Validation.checkEmptyString(productName, "Product name");
        Validation.checkEmptyString(category, "Product category");
        Validation.checkPositiveDouble(price, "Product price");
        Validation.checkPositiveInt(stockQuantity, "Stock quantity");

        this.productId      = productId;
        this.productName    = productName;
        this.category       = category;
        this.price          = price;
        this.stockQuantity  = stockQuantity;
    }

    // Getters & Setters-------
    public String getProductId()                   { return productId; }
    public void   setProductId(String id)          { this.productId = id; }

    public String getProductName()                 { return productName; }
    public void   setProductName(String name)      { this.productName = name; }

    public String getCategory()                    { return category; }
    public void   setCategory(String category)     { this.category = category; }

    public double getPrice()                       { return price; }
    public void   setPrice(double price)           { this.price = price; }

    public int    getStockQuantity()               { return stockQuantity; }
    public void   setStockQuantity(int qty)        { this.stockQuantity = qty; }

    // toString method for easy display
    @Override
    public String toString() {
        return String.format("ID: %-6s | %-20s | %-12s | Price: %10.2f | Stock: %d",
                productId, productName, category, price, stockQuantity);
    }
}