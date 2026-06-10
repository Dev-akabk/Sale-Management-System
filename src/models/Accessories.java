package entities;

import exceptions.InvalidInputException;

public class Accessories extends Product {

    private final String size;

    public Accessories(String productId, String productName, String category, double price, int stockQuantity, String size)
            throws InvalidInputException {
        super(productId, productName, category, price, stockQuantity);
        this.size = size;
    }

    public double calculateDiscount() {
        return getPrice() * 0.15;
    }

    @Override
    public String toString() {
        return super.toString() + " -> Clothing [Size=" + size + ", Discount=" + calculateDiscount() + "]";
    }
}
