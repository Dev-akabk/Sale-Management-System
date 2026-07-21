package models;

import java.io.Serializable;
import utils.Validation;
import exceptions.InvalidInputException;

//customer la lop cha(vip, regular)
public abstract class Customer implements Serializable{
    //Fields (Encapsulation)
    private String customerId;
    private String name;
    private String phone;
    private String address;
    private double totalSpend;
    private static final long serialVersionUID = 1L;

    // Constructor
    public Customer(String customerId, String name, String phone, String address)
             throws InvalidInputException {
        // Validate input data
        Validation.checkCustomerIdFormat(customerId);
        Validation.checkEmptyString(name,    "Customer name");
        Validation.checkEmptyString(phone,   "Customer phone");
        Validation.checkPhone(phone);
        Validation.checkEmptyString(address, "Customer address");

        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.totalSpend = 0.0;
    }

    // Getters & Setters---------------------------------------------
    public String getCustomerId()             { return customerId; }
    public void   setCustomerId(String id)    { this.customerId = id; }

    public String getName()                   { return name; }
    public void   setName(String name)        { this.name = name; }

    public String getPhone()                  { return phone; }
    public void   setPhone(String phone)      { this.phone = phone; }

    public String getAddress()                { return address; }
    public void   setAddress(String address)  { this.address = address; }

    public double getTotalSpend()             { return totalSpend; }
    public void   setTotalSpend(double spend) { this.totalSpend = spend; }

    /*
    Abstract method
    -RegularCustomer: use loyalty points discount
    -VipCustomer: 10% discount
     */
    public abstract double calculateTotal(double baseAmount);

    // Returns a formatted info block as a String instead of printing directly.
    // Model layer must not perform I/O — the View decides how/where to display it.
    public abstract String displayInfo();

    @Override
    public String toString() {
        return String.format("ID: %-6s | Name: %-20s | Phone: %s | Address: %-15s | Spent: %.2f",
                customerId, name, phone, address, totalSpend);
    }
}