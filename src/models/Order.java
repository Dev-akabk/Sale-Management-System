package models;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import exceptions.InvalidInputException;
import utils.Validation;


//LocalDate is used to get current date for easy sort and filter by date

public class Order {
    private String            orderId;
    private LocalDate         orderDate;
    private double            totalAmount;
    private Customer          customer;
    private List<OrderDetail> orderDetails;

    // Constructor
    public Order(String orderId, Customer customer)
        throws InvalidInputException {
        Validation.checkOrderIdFormat(orderId);
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null for an order!");
        }
        Validation.checkEmptyString(customer.getCustomerId(), "Customer ID");
        Validation.checkEmptyString(customer.getName(),       "Customer name");
        Validation.checkEmptyString(customer.getPhone(),      "Customer phone");
        Validation.checkPhone(customer.getPhone());
        Validation.checkEmptyString(customer.getAddress(),    "Customer address");
        
        this.orderId      = orderId;
        this.customer     = customer;
        this.orderDetails = new ArrayList<>();
        this.orderDate    = LocalDate.now(); // Current date
        this.totalAmount  = 0.0;
    }

    // Getters & Setters-------------------------------------------
    public String            getOrderId()                        { return orderId; }
    public void              setOrderId(String id)               { this.orderId = id; }

    public LocalDate         getOrderDate()                      { return orderDate; }
    public void              setOrderDate(LocalDate date)        { this.orderDate = date; }

    public double            getTotalAmount()                    { return totalAmount; }
    public void              setTotalAmount(double amount)       { this.totalAmount = amount; }

    public Customer          getCustomer()                       { return customer; }
    public void              setCustomer(Customer c)             { this.customer = c; }

    public List<OrderDetail> getOrderDetails()                   { return orderDetails; }
    public void              setOrderDetails(List<OrderDetail> d){ this.orderDetails = d; }

    // Add item
    public void addProduct(Product product, int quantity) {
        this.orderDetails.add(new OrderDetail(product, quantity));
    }

    // Calc total
    public void calculateTotal() {
        double subtotal = 0.0;
        for (OrderDetail detail : orderDetails) {
            //san pham * gia tien * so luong
            subtotal += detail.getProduct().getPrice() * detail.getQuantity();
        }
        
        // VIP discount check
        // if (customer instanceof VIPCustomer) {
        //     VIPCustomer vip = (VIPCustomer) customer;
        //     sum = sum * (1 - vip.getDiscountRate());
        // }
        
        //khong can instanceof, chi can goi method calculateTotal cua customer,
        // no se tu dong check loai khach hang va ap dung discount tuong ung
        this.totalAmount =  customer.calculateTotal(subtotal);
    }

    @Override
    public String toString() {
        return String.format("Order ID: %-6s | Customer: %-20s | Date: %s | Total: %.2f VND",
                orderId, customer.getName(), orderDate, totalAmount);
    }
}