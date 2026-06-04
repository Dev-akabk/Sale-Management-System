package controllers;

import entities.Customer;
import entities.Order;
import entities.Product;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import exceptions.OutOfStockException;
import java.util.ArrayList;
import java.util.List;
import utils.Validation;

public class OrderList {

    private final List<Order> orders;

    public OrderList() {
        this.orders = new ArrayList<>();
    }


    public void createOrder(String orderId, Customer customer)
            throws InvalidInputException {
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null when creating an order!");
        }

        Validation.checkOrderIdFormat(orderId);

        if (findOrderById(orderId) != null) {
            throw new InvalidInputException(
                    "Order ID " + orderId + " already exists!");
        }

        this.orders.add(new Order(orderId, customer));
        System.out.println("Order created successfully.\n");
    }


    public void addProductToOrder(String orderId, Product product, int quantity)
            throws ItemNotFoundException, InvalidInputException, OutOfStockException {
        Order foundOrder = findOrderById(orderId);
        if (foundOrder == null) {
            throw new ItemNotFoundException(
                    "Order with ID " + orderId + " not found!");
        }

        if (product == null) {
            throw new InvalidInputException("Product cannot be null!");
        }

        Validation.checkPositiveInt(quantity, "Quantity");

        if (product.getStockQuantity() < quantity) {
            throw new OutOfStockException(
                    "Insufficient stock for product '" + product.getProductName()
                    + "'. Available: " + product.getStockQuantity()
                    + ", Requested: " + quantity);
        }

        foundOrder.addProduct(product, quantity);
        product.setStockQuantity(product.getStockQuantity() - quantity);
        foundOrder.calculateTotal();
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
        Order foundOrder = findOrderById(orderId);
        if (foundOrder == null) {
            throw new ItemNotFoundException(
                    "Order with ID " + orderId + " not found!");
        }
        System.out.println("=== Order Details: " + orderId + " ===");
        System.out.println("Customer : " + foundOrder.getCustomer().getName());
        System.out.println("Date     : " + foundOrder.getOrderDate());
        System.out.println("Items:");
        foundOrder.getOrderDetails().forEach(d -> System.out.println("  " + d));
        System.out.printf("Total    : %.2f%n%n", foundOrder.getTotalAmount());
    }

    // =========================================================================
    // findOrderById — returns null if not found
    // =========================================================================
    public Order findOrderById(String orderId) {
        for (Order o : orders) {
            if (o.getOrderId().equalsIgnoreCase(orderId)) {
                return o;
            }
        }
        return null;
    }


    public boolean removeOrderById(String orderId) throws ItemNotFoundException {
        Order foundOrder = findOrderById(orderId);
        if (foundOrder == null) {
            throw new ItemNotFoundException(
                    "Order with ID " + orderId + " not found for removal!");
        }
        return orders.remove(foundOrder);
    }

    public List<Order> getOrders() {
        return this.orders;
    }
}