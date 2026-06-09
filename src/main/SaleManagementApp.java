package ui;

import controllers.CustomerList;
import controllers.OrderList;
import controllers.ProductList;
import entities.Customer;
import entities.Product;
import entities.VIPCustomer;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import exceptions.OutOfStockException;

public class Main {

    public static void main(String[] args) {

        // ── Product tests ──────────────────────────────────────────────────────
        System.out.println("===== PRODUCT MANAGER =====");
        ProductList productList = new ProductList();

        System.out.println("-- Testing empty list:");
        

        try {
            productList.displayAllProducts();

            Product p1 = new Product("P_001", "Laptop",     "Electronics", 999.99, 20);
            Product p2 = new Product("P_002", "Smartphone", "Electronics", 699.99, 30);
            Product p3 = new Product("P_003", "Keyboard",   "Accessories", 149.99, 50);

            System.out.println("-- Adding products:");
            productList.addProduct(p1);
            productList.addProduct(p2);
            productList.addProduct(p3);

            System.out.println("-- All products:");
            productList.displayAllProducts();

            System.out.println("-- Update P_002:");
            productList.updateProduct("P_002", "Samsung Galaxy S25", 749.99);
            productList.displayAllProducts();

            System.out.println("-- Remove P_001:");
            productList.removeProductById("P_001");
            productList.displayAllProducts();

        } catch (InvalidInputException | ItemNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // ── Customer tests ─────────────────────────────────────────────────────
        System.out.println("\n===== CUSTOMER MANAGER =====");
        CustomerList customerList = new CustomerList();

        try {
            Customer c1 = new Customer("C_001", "Nguyen Van A", "0901234567", "Ha Noi");
            Customer c2 = new VIPCustomer("C_002", "Tran Thi B", "0987654321", "HCM City", 0.10);

            System.out.println("-- Adding customers:");
            customerList.addCustomer(c1);
            customerList.addCustomer(c2);

            System.out.println("-- All customers:");
            customerList.displayAllCustomers();

            System.out.println("-- Update C_001:");
            customerList.updateCustomer("C_001", "Nguyen Van An", "0912345678");
            customerList.displayAllCustomers();
            System.out.println("-- Remove C_001:");
            customerList.removeCustomerById("C_001");
            customerList.displayAllCustomers();

        } catch (InvalidInputException | ItemNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // ── Order tests ────────────────────────────────────────────────────────
        System.out.println("\n===== ORDER MANAGER =====");
        OrderList orderList = new OrderList();

        try {
            Customer vip = customerList.findCustomerById("C_002");
            Product laptop = new Product("P_004", "Gaming Laptop", "Electronics", 1500.00, 10);

            System.out.println("-- Creating order:");
            orderList.createOrder("O_001", vip);

            System.out.println("-- Adding product to order:");
            orderList.addProductToOrder("O_001", laptop, 2);

            System.out.println("-- Order details:");
            orderList.displayOrderDetails("O_001");

        } catch (InvalidInputException | ItemNotFoundException | OutOfStockException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // ── Exception handling tests ───────────────────────────────────────────
        System.out.println("\n===== EXCEPTION TESTS =====");
        ProductList pl = new ProductList();
        try {
            pl.addProduct(null);
        } catch (InvalidInputException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        try {
            pl.removeProductById("P_999");
        } catch (ItemNotFoundException | InvalidInputException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
    }
}