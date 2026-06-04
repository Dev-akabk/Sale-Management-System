package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private Date orderDate;
    private double totalAmount;
    private Customer customer;
    private List<OrderDetail> orderDetails;

    // Constructor
    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDetails = new ArrayList<>();
        this.orderDate = new Date(); // Current date
        this.totalAmount = 0.0;
    }

    // Getters & Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    // Add item
    public void addProduct(Product product, int quantity) {
        this.orderDetails.add(new OrderDetail(product, quantity));
    }

    // Calc total
    public void calculateTotal() {
        double sum = 0.0;
        for (OrderDetail detail : orderDetails) {
            sum += detail.getProduct().getPrice() * detail.getQuantity();
        }
        
        // VIP discount check
        if (customer instanceof VIPCustomer) {
            VIPCustomer vip = (VIPCustomer) customer;
            sum = sum * (1 - vip.getDiscountRate());
        }
        
        this.totalAmount = sum;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", customer=" + customer.getName() + 
               ", totalAmount=" + totalAmount + ", orderDate=" + orderDate + '}';
    }
}