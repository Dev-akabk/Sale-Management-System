package models;

import utils.Validation;
import exceptions.InvalidInputException;
public class VIPCustomer extends Customer {
    private double discountRate; //0.0-0.1

    // Constructor
    public VIPCustomer(String customerId, String name, String phone, String address,
                       double discountRate) 
            throws InvalidInputException {
        super(customerId, name, phone, address);
        // Validation.checkNonNegativeDouble(discountRate, "Discount rate");
        // Validation.checkPositiveDouble(discountRate, "Discount rate");
        //bo validation checkNonNegativeDouble va checkPositiveDouble vi discountRate co the la 0 
        //(khong co giam gia) hoac 0.1 (giam gia 10%), 0.2 (giam gia 20%)...
        Validation.checkDiscountRate(discountRate);
        this.discountRate = discountRate;
    }

    // Getters & Setters
    public double getDiscountRate()                { return discountRate; }
    public void   setDiscountRate(double rate)     { this.discountRate = rate; }
    
    //display info
    @Override
    public void displayInfo() {
        System.out.println("  Type          : VIP CUSTOMER");
        System.out.printf ("  ID            : %s  |  Name: %s%n",  getCustomerId(), getName());
        System.out.printf ("  Phone         : %s  |  Address: %s%n", getPhone(), getAddress());
        System.out.printf ("  Discount Rate : %.0f%%  |  Total Spend: %.2f VND%n",
                discountRate * 100, getTotalSpend());
    }
    //toString
    @Override
    public String toString() {
        return "[VIP]     " + super.toString()
                + String.format(" | Discount: %.0f%%", discountRate * 100);
    }
}