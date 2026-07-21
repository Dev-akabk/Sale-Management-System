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

    @Override
    public double calculateTotal(double baseAmount) {
        return baseAmount * (1.0 - discountRate);
    }

    // Builds the info block as a String — no I/O performed here.
    // The caller (View layer) decides how and where to print it.
    @Override
    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Type          : VIP CUSTOMER\n");
        sb.append(String.format("  ID            : %s  |  Name: %s%n", getCustomerId(), getName()));
        sb.append(String.format("  Phone         : %s  |  Address: %s%n", getPhone(), getAddress()));
        sb.append(String.format("  Discount Rate : %.0f%%  |  Total Spend: %.2f VND%n",
                discountRate * 100, getTotalSpend()));
        return sb.toString();
    }

    //toString
    @Override
    public String toString() {
        return "[VIP]     " + super.toString()
                + String.format(" | Discount: %.0f%%", discountRate * 100);
    }
}