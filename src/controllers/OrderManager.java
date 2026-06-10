package controllers;

import models.Customer;
import models.Order;
import models.OrderDetail;
import models.Product;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import exceptions.OutOfStockException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import utils.Validation;

public class OrderManager {

    private final List<Order> orders = new ArrayList<>();


    //CRUD methods:
    public void createOrder(String orderId, Customer customer)
            throws InvalidInputException {
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null!");
        }
        Validation.checkOrderIdFormat(orderId);
        if (findOrderById(orderId) != null) {
            throw new InvalidInputException(
                    "Order ID " + orderId + " already exists!");
        }

        this.orders.add(new Order(orderId, customer));
        System.out.println("Order created successfully.\n");
    }

    // =========================================================================
    //Quy trinh them san pham vao don hang:
    // 1. Tim order theo ID co ton tai hay khong
    // 2. Kiem tra san pham co null hay khong
    // 3. Kiem tra quantity > 0
    // 4. Kiem tra ton kho du? -> nem OutOfStockException neu khong du
    // 5. Tru stock, them detail, tinh total lai
    public void addProductToOrder(String orderId, Product product, int quantity)
            throws ItemNotFoundException, InvalidInputException, OutOfStockException {
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new ItemNotFoundException(
                    "Order with ID " + orderId + " not found!");
        }

        if (product == null) {
            throw new InvalidInputException("Product cannot be null!");
        }

        Validation.checkPositiveInt(quantity, "Quantity");

        if (product.getStockQuantity() < quantity) {
            throw new OutOfStockException(
                    "Insufficient stock for '" + product.getProductName()
                    + "'. Available: " + product.getStockQuantity()
                    + ", Requested: " + quantity);
        }
        //Tru ton kho san pham
        product.setStockQuantity(product.getStockQuantity() - quantity);
        //Them vao order va tinh lai total
        order.addProduct(product, quantity);
        order.calculateTotal();

        System.out.println("Product added to order successfully.\n");
    }

    // =========================================================================
    // displayAllOrders
    // =========================================================================
    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("The order list is empty.");
            return;
        }
        for (Order o : orders) {
            System.out.println(o.toString());
        }
    }

    // =========================================================================
    // displayOrderDetails — shows the full item breakdown for one order
    // =========================================================================
public void displayOrderDetails(String orderId) throws ItemNotFoundException {
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new ItemNotFoundException("Order with ID " + orderId + " not found!");
        }

        //Format ngay thang cho de doc
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("=========================================");
        System.out.println("           ORDER DETAILS: " + orderId);
        System.out.println("=========================================");
        System.out.println("Customer : " + order.getCustomer().getName());
        System.out.println("Date     : " + dtf.format(order.getOrderDate()));
        System.out.println("-----------------------------------------");
        System.out.printf("%-20s | %-5s | %-10s%n", "Product Name", "Qty", "Subtotal");
        System.out.println("-----------------------------------------");
        
        // 3. In chi tiết từng món hàng thành dạng bảng
        for (OrderDetail detail : order.getOrderDetails()) {
            double subTotal = detail.getProduct().getPrice() * detail.getQuantity();
            System.out.printf("%-20s | %-5d | $%.2f%n", 
                    detail.getProduct().getProductName(), 
                    detail.getQuantity(), 
                    subTotal);
        }
        
        System.out.println("-----------------------------------------");
        System.out.printf("FINAL TOTAL: $%.2f%n", order.getTotalAmount()); // Lưu ý: Tên hàm getter tổng tiền tùy bạn đặt trong Order.java
        System.out.println("=========================================\n");
    }

    // =========================================================================
    // findOrderById — returns null if not found
    // =========================================================================
    public Order findOrderById(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) return null;
        return orders.stream()
                .filter(o -> o.getOrderId().equalsIgnoreCase(orderId))
                .findFirst().orElse(null);
    }

    //Tim kiem de in ra man hinh
    public void searchOrder(String keyword) throws InvalidInputException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidInputException("Search keyword cannot be empty!");
        }

        System.out.println("\n--- SEARCH RESULTS ---");
        boolean found = false;
        
        for (Order o : orders) {
            // Search by order ID
            if (o.getOrderId().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(o.toString());
                found = true;
            }
        }
        //bien linh canh
        if (!found) {
            System.out.println("No orders found matching keyword: '" + keyword + "'");
        }
        System.out.println("----------------------\n");
    }


    public void removeOrderById(String orderId) throws ItemNotFoundException {
        Order findOrder = findOrderById(orderId);
        if (findOrder == null) {
            throw new ItemNotFoundException(
                    "Order with ID " + orderId + " not found for removal!");
        }
        orders.remove(findOrder);
        System.out.println("Order removed successfully.\n");
    }

    public List<Order> getOrders() {
        return this.orders;
    }
}