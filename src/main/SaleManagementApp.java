package main;

import controllers.CustomerManager;
import controllers.OrderManager;
import controllers.ProductList;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import exceptions.OutOfStockException;
import models.Customer;
import models.Product;
import models.RegularCustomer;
import models.VIPCustomer;
import views.ConsoleMenu;

import java.util.Scanner;

public class SaleManagementApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductList productManager = new ProductList();
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
                    manageProducts();
                    break;
                case "2":
                    manageCustomers();
                    break;
                case "3":
                    manageOrders();
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

    private static void manageProducts() {
        boolean productRunning = true;
        while (productRunning) {
            ConsoleMenu.displayProductMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter Product ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Category: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter Price: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter Stock Quantity: ");
                        int stock = Integer.parseInt(scanner.nextLine());

                        productManager.addProduct(new Product(id, name, category, price, stock));
                        break;
                    case "2":
                        productManager.displayAllProducts();
                        break;
                    case "3":
                        System.out.print("Enter Product ID to update: ");
                        String uId = scanner.nextLine();
                        System.out.print("Enter New Name: ");
                        String uName = scanner.nextLine();
                        System.out.print("Enter New Price: ");
                        double uPrice = Double.parseDouble(scanner.nextLine());
                        productManager.updateProduct(uId, uName, uPrice);
                        break;
                    case "4":
                        System.out.print("Enter Product ID to remove: ");
                        String rId = scanner.nextLine();
                        productManager.removeProductById(rId);
                        System.out.println("Product removed successfully.\n");
                        break;
                    case "5":
                        productRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid number format!");
            } catch (InvalidInputException | ItemNotFoundException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void manageCustomers() {
        boolean customerRunning = true;
        while (customerRunning) {
            ConsoleMenu.displayCustomerMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter Customer ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter Address: ");
                        String address = scanner.nextLine();
                        System.out.print("Is VIP? (y/n): ");
                        String isVip = scanner.nextLine();

                        if (isVip.equalsIgnoreCase("y")) {
                            System.out.print("Enter Discount Rate (e.g. 0.1 for 10%): ");
                            double rate = Double.parseDouble(scanner.nextLine());
                            customerManager.addCustomer(new VIPCustomer(id, name, phone, address, rate));
                        } else {
                            System.out.print("Enter Loyalty Points: ");
                            int points = Integer.parseInt(scanner.nextLine());
                            customerManager.addCustomer(new RegularCustomer(id, name, phone, address, points));
                        }
                        break;
                    case "2":
                        customerManager.displayAllCustomers();
                        break;
                    case "3":
                        System.out.print("Enter Customer ID to update: ");
                        String uId = scanner.nextLine();
                        System.out.print("Enter New Name: ");
                        String uName = scanner.nextLine();
                        System.out.print("Enter New Phone: ");
                        String uPhone = scanner.nextLine();
                        customerManager.updateCustomer(uId, uName, uPhone);
                        break;
                    case "4":
                        System.out.print("Enter Customer ID to remove: ");
                        String rId = scanner.nextLine();
                        customerManager.removeCustomerById(rId);
                        break;
                    case "5":
                        customerRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid number format!");
            } catch (InvalidInputException | ItemNotFoundException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void manageOrders() {
        boolean orderRunning = true;
        while (orderRunning) {
            ConsoleMenu.displayOrderMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter New Order ID: ");
                        String oId = scanner.nextLine();
                        System.out.print("Enter Customer ID: ");
                        String cId = scanner.nextLine();
                        
                        Customer customer = customerManager.findCustomerById(cId);
                        if (customer == null) {
                            System.out.println("ERROR: Customer not found!");
                        } else {
                            orderManager.createOrder(oId, customer);
                        }
                        break;
                    case "2":
                        System.out.print("Enter Order ID: ");
                        String targetOId = scanner.nextLine();
                        System.out.print("Enter Product ID: ");
                        String pId = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine());

                        Product product = productManager.findProductById(pId);
                        if (product == null) {
                            System.out.println("ERROR: Product not found!");
                        } else {
                            orderManager.addProductToOrder(targetOId, product, qty);
                        }
                        break;
                    case "3":
                        System.out.print("Enter Order ID to display details: ");
                        String detailsId = scanner.nextLine();
                        orderManager.displayOrderDetails(detailsId);
                        break;
                    case "4":
                        System.out.print("Enter Order ID to remove: ");
                        String rId = scanner.nextLine();
                        orderManager.removeOrderById(rId);
                        break;
                    case "5":
                        orderRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid number format!");
            } catch (InvalidInputException | ItemNotFoundException | OutOfStockException e) {
                System.out.println("ERROR: " + e.getMessage());
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