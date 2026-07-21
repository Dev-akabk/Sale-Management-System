package controllers;

import models.Customer;
import models.Order;
import models.Product;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import exceptions.OutOfStockException;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import utils.Validation;

public class OrderManager {
    //add modifier final to prevent external modification of the list
    private final List<Order> orders = new ArrayList<Order>();


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
        // Success messaging delegated to view layer
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

        // Success messaging delegated to view layer
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

    /**
     * Stream-based keyword search over order ID.
     * Returns a filtered list for the view layer to display.
     *
     * @param keyword  search term (matched case-insensitively against order ID)
     * @return list of matching orders (empty list if none found)
     * @throws InvalidInputException if keyword is null or blank
     */
    public List<Order> searchOrder(String keyword) throws InvalidInputException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidInputException("Search keyword cannot be empty!");
        }
        String lowerKey = keyword.toLowerCase().trim();
        return orders.stream()
                .filter(o -> o.getOrderId().toLowerCase().contains(lowerKey))
                .collect(Collectors.toList());
    }


    public void removeOrderById(String orderId) throws ItemNotFoundException {
        Order findOrder = findOrderById(orderId);
        if (findOrder == null) {
            throw new ItemNotFoundException(
                    "Order with ID " + orderId + " not found for removal!");
        }
        orders.remove(findOrder);
        // Success messaging delegated to view layer
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(this.orders);
    }
}