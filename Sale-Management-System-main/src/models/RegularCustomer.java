package models;

import utils.Validation;
import exceptions.InvalidInputException;


//10,000VND cho moi 1,000 loyalty points
//discount toi da 20% tong don hang (maxDiscount)
//loyalty se reset sau khi dung
public class RegularCustomer extends Customer{

    private int loyaltyPoints;

    // Constructor
    public RegularCustomer(String customerId, String name, String phone,
                           String address, int loyaltyPoints)
            throws InvalidInputException {
        super(customerId, name, phone, address);
        //Validation
        Validation.checkNonNegativeInt(loyaltyPoints, "Loyalty points");
        
        this.loyaltyPoints = loyaltyPoints;
    }

    // Getters & Setters
    public int  getLoyaltyPoints()             { return loyaltyPoints; }
    public void setLoyaltyPoints(int points)   { this.loyaltyPoints = points; }

    //Override for calculate total amount after discount
    @Override
    public double calculateTotal(double baseAmount) {
        double maxDiscount    = baseAmount * 0.20;
        double earnedDiscount = (loyaltyPoints / 1000.0) * 10_000.0;
        double discount       = Math.min(earnedDiscount, maxDiscount);
        return baseAmount - discount;
    }

    // Builds the info block as a String — no I/O performed here.
    // The caller (View layer) decides how and where to print it.
    @Override
    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Type          : REGULAR CUSTOMER\n");
        sb.append(String.format("  ID            : %s  |  Name: %s%n", getCustomerId(), getName()));
        sb.append(String.format("  Phone         : %s  |  Address: %s%n", getPhone(), getAddress()));
        sb.append(String.format("  Loyalty Points: %d  |  Total Spend: %.2f VND%n",
                loyaltyPoints, getTotalSpend()));
        return sb.toString();
    }

    @Override
    public String toString() {
        return "[REGULAR] " + super.toString()
                + String.format(" | LoyaltyPts: %d", loyaltyPoints);
    }
}