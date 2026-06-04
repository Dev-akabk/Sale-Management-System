package entities;
import utils.Validation;
import exceptions.InvalidInputException;
public class VIPCustomer extends Customer {
    private double discountRate;

    // Constructor
    public VIPCustomer(String customerId, String name, String phone, String address, double discountRate) throws InvalidInputException {
        super(customerId, name, phone, address);
        Validation.checkNonNegativeDouble(discountRate, "Discount rate");
        Validation.checkPositiveDouble(discountRate, "Discount rate");
        this.discountRate = discountRate;
    }

    // Getters & Setters
    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public String toString() {
        return "VIPCustomer{" + super.toString() + " discountRate=" + discountRate + '}';
    }
}