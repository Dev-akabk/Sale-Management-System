package models;

import utils.Validation;
import exceptions.InvalidInputException;

public class RegularCustomer extends Customer {
    private int loyaltyPoints;

    // Constructor
    public RegularCustomer(String customerId, String name, String phone, String address, int loyaltyPoints) throws InvalidInputException {
        super(customerId, name, phone, address);
        
        //Validation data before inputting
        Validation.checkNonNegativeInt(loyaltyPoints, "Loyalty points");
        
        this.loyaltyPoints = loyaltyPoints;
    }

    // Getters & Setters
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    //Override for calculate total amount after discount
    @Override
    public double calculateTotal(double baseAmount) {
        // vip customer have 10% discount, regular customer have no discount,
        // but they can use loyalty points to get discount on their purchase.
        // 10k VND for each 1000 loyalty points,
        // and the maximum discount from loyalty points is 20% of the total amount.
        // {Calculate discount from loyalty points}, {ensure not exceed 20% of total amount}
        double discount = Math.min(loyaltyPoints / 1000.0 * 10000, baseAmount * 0.2);
        return baseAmount - discount;
    }

    //Override for display customer information
    @Override
    public void displayInfo() {
        System.out.println("Type: REGULAR CUSTOMER");
        System.out.println("ID: " + getCustomerId() + " | Name: " + getName());
        System.out.println("Phone: " + getPhone() + " | Address: " + getAddress());
        System.out.println("Loyalty Points: " + loyaltyPoints + " | Total Spend: " + getTotalSpend() + " VND");
    }

    @Override
    public String toString() {
        return "RegularCustomer{" + super.toString() + " loyaltyPoints=" + loyaltyPoints + '}';
    }
}