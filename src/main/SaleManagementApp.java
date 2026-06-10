package main;

import controllers.CustomerManager;
import controllers.OrderManager;
import controllers.ProductManager;
import models.Product;
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
            productManager.addProduct(new Product("P_001", "Laptop", "Electronics", 999.99, 20));
            productManager.addProduct(new Product("P_002", "Smartphone", "Electronics", 699.99, 30));
            productManager.addProduct(new Product("P_003", "Keyboard", "Accessories", 149.99, 50));

            customerManager.addCustomer(new RegularCustomer("C_001", "Nguyen Van A", "0901234567", "Ha Noi", 100));
            customerManager.addCustomer(new VIPCustomer("C_002", "Tran Thi B", "0987654321", "HCM City", 0.10));

            orderManager.createOrder("O_001", customerManager.findCustomerById("C_002"));
            orderManager.addProductToOrder("O_001", productManager.findProductById("P_001"), 2);
        } catch (Exception e) {
            System.out.println("Failed to initialize mock data: " + e.getMessage());
        }
    }
}