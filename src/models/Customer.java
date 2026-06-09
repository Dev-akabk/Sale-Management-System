package models;

import utils.Validation;
import exceptions.InvalidInputException;

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String address;
    private double totalSpend;

    // Constructor
    public Customer(String customerId, String name, String phone, String address) throws InvalidInputException {
        // Validate input data
        Validation.checkCustomerIdFormat(customerId);
        Validation.checkEmptyString(name, "Customer name");
        Validation.checkEmptyString(phone, "Customer phone");
        Validation.checkPhone(phone);
        Validation.checkEmptyString(address, "Customer address");

        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.totalSpend = 0.0;
    }

    // Getters & Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public abstract double calculateTotal(double baseAmount);

    public abstract void displayInfo();

    @Override
    public String toString() {
        return "customerId=" + customerId + ", name=" + name + 
               ", phone=" + phone + ", address=" + address + ", totalSpend=" + totalSpend;
    }
}