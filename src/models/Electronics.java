package models;

import exceptions.InvalidInputException;

public class Electronics extends Product {

    private final int warrantyMonths;

    public Electronics(String productId, String productName, String category, double price, int stockQuantity, int warrantyMonths)
            throws InvalidInputException {
        super(productId, productName, category, price, stockQuantity);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public double calculateDiscount() {
        if (getPrice() > 500) {
            return getPrice() * 0.10;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return super.toString() + "-> Electronics [Warranty=" + warrantyMonths + " months, Discount=" + calculateDiscount() + "]";
    }
}
