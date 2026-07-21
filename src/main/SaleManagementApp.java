package main;

import controllers.CustomerManager;
import controllers.OrderManager;
import controllers.ProductManager;
import models.Electronics;
import models.Accessories;
import models.RegularCustomer;
import models.VIPCustomer;
import views.ConsoleMenu;

import java.util.Scanner;

public class SaleManagementApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductManager productManager = new ProductManager();
    private static final CustomerManager customerManager = new CustomerManager();
    private static final OrderManager orderManager = new OrderManager();

    public static void main(String[] args) {
        initializeMockData();

        boolean running = true;
        while (running) {
            ConsoleMenu.displayMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    ConsoleMenu.manageProducts(productManager, scanner);
                    break;
                case "2":
                    ConsoleMenu.manageCustomers(customerManager, scanner);
                    break;
                case "3":
                    ConsoleMenu.manageOrders(orderManager, productManager, customerManager, scanner);
                    break;
                case "4":
                    ConsoleMenu.showReports(orderManager, productManager, customerManager, scanner);
                    break;
                case "5":
                    System.out.println("Exiting System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }

    // Initialize hardcoded data for quick testing
    private static void initializeMockData() {
        try {
            // ── PRODUCTS ──────────────────────────────────────────────────────
            // Electronics (price > 500 → 10% discount)
            productManager.addProduct(new Electronics("P_001", "Laptop Pro 15",    "Electronics",  999.99, 20, 24));
            productManager.addProduct(new Electronics("P_002", "Smartphone X12",   "Electronics",  699.99, 30, 12));
            productManager.addProduct(new Electronics("P_003", "4K Smart TV 55",   "Electronics", 1199.99, 10, 36));
            productManager.addProduct(new Electronics("P_004", "Wireless Earbuds", "Electronics",  249.99, 50,  6));
            productManager.addProduct(new Electronics("P_005", "Gaming Console",   "Electronics",  549.99, 15, 12));

            // Accessories (flat 15% discount)
            productManager.addProduct(new Accessories("P_006", "Mechanical Keyboard", "Accessories", 149.99, 60, "Standard"));
            productManager.addProduct(new Accessories("P_007", "Laptop Backpack",      "Accessories",  79.99, 80, "Large"));
            productManager.addProduct(new Accessories("P_008", "USB-C Hub 7-in-1",     "Accessories",  49.99, 100, "Universal"));

            // ── CUSTOMERS ─────────────────────────────────────────────────────
            // Regular customers (loyalty points)
            customerManager.addCustomer(new RegularCustomer("C_001", "Nguyen Van A",  "0901234567", "Ha Noi",    500));
            customerManager.addCustomer(new RegularCustomer("C_003", "Le Thi C",      "0912345678", "Da Nang",  1200));
            customerManager.addCustomer(new RegularCustomer("C_004", "Pham Minh D",   "0923456789", "Can Tho",     0));

            // VIP customers (percentage discount)
            customerManager.addCustomer(new VIPCustomer("C_002", "Tran Thi B",   "0987654321", "HCM City", 0.10)); // 10% off
            customerManager.addCustomer(new VIPCustomer("C_005", "Hoang Van E",  "0934567890", "Hue",      0.20)); // 20% off

            // ── ORDERS ────────────────────────────────────────────────────────
            // Order 1: VIP C_002 mua Laptop + Earbuds
            orderManager.createOrder("O_001", customerManager.findCustomerById("C_002"));
            orderManager.addProductToOrder("O_001", productManager.findProductById("P_001"), 2);
            orderManager.addProductToOrder("O_001", productManager.findProductById("P_004"), 1);

            // Order 2: Regular C_001 (500 pts) mua Smart TV
            orderManager.createOrder("O_002", customerManager.findCustomerById("C_001"));
            orderManager.addProductToOrder("O_002", productManager.findProductById("P_003"), 1);
            orderManager.addProductToOrder("O_002", productManager.findProductById("P_006"), 2);

            // Order 3: VIP C_005 (20% off) mua Gaming Console + Backpack
            orderManager.createOrder("O_003", customerManager.findCustomerById("C_005"));
            orderManager.addProductToOrder("O_003", productManager.findProductById("P_005"), 3);
            orderManager.addProductToOrder("O_003", productManager.findProductById("P_007"), 2);

            // Order 4: Regular C_003 (1200 pts → max 20% discount) mua Smartphone + USB Hub
            orderManager.createOrder("O_004", customerManager.findCustomerById("C_003"));
            orderManager.addProductToOrder("O_004", productManager.findProductById("P_002"), 2);
            orderManager.addProductToOrder("O_004", productManager.findProductById("P_008"), 3);

            // Order 5: Regular C_004 (0 pts, no discount) mua Keyboard + Backpack
            orderManager.createOrder("O_005", customerManager.findCustomerById("C_004"));
            orderManager.addProductToOrder("O_005", productManager.findProductById("P_006"), 1);
            orderManager.addProductToOrder("O_005", productManager.findProductById("P_007"), 1);

            System.out.println("==============================================");
            System.out.println("  Mock data initialized! Summary:");
            System.out.println("  - 8 Products  (5 Electronics, 3 Accessories)");
            System.out.println("  - 5 Customers (3 Regular, 2 VIP)");
            System.out.println("  - 5 Orders    (O_001 to O_005)");
            System.out.println("==============================================\n");

        } catch (Exception e) {
            System.out.println("Failed to initialize mock data: " + e.getMessage());
        }
    }
}