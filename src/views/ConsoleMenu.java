package views;

import controllers.CustomerManager;
import controllers.OrderManager;
import controllers.ProductManager;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import exceptions.OutOfStockException;
import models.Customer;
import models.Product;
import models.RegularCustomer;
import models.VIPCustomer;

import java.util.Scanner;

public class ConsoleMenu {
    
    public static void displayMainMenu() {
        System.out.println("\n====================================");
        System.out.println("      SALE MANAGEMENT SYSTEM        ");
        System.out.println("====================================");
        System.out.println("1. Product Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Order Management");
        System.out.println("4. Exit");
        System.out.println("====================================");
        System.out.print("Please choose an option (1-4): ");
    }

    public static void displayProductMenu() {
        System.out.println("\n--- PRODUCT MANAGEMENT ---");
        System.out.println("1. Add Product");
        System.out.println("2. Display All Products");
        System.out.println("3. Search Product");
        System.out.println("4. Update Product");
        System.out.println("5. Remove Product");
        System.out.println("0. Back to Main Menu");
        System.out.print("Please choose an option (0-5): ");
    }

    public static void displayCustomerMenu() {
        System.out.println("\n--- CUSTOMER MANAGEMENT ---");
        System.out.println("1. Add Customer");
        System.out.println("2. Display All Customers");
        System.out.println("3. Search Customer");
        System.out.println("4. Update Customer");
        System.out.println("5. Remove Customer");
        System.out.println("0. Back to Main Menu");
        System.out.print("Please choose an option (0-5): ");
    }

    public static void displayOrderMenu() {
        System.out.println("\n--- ORDER MANAGEMENT ---");
        System.out.println("1. Create New Order");
        System.out.println("2. Add Product to Order");
        System.out.println("3. Display Order Details");
        System.out.println("4. Remove Order");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please choose an option (1-5): ");
    }

    public static void manageProducts(ProductManager productManager, Scanner scanner) {
        boolean productRunning = true;
        while (productRunning) {
            displayProductMenu();
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
                        System.out.println("We can search by keyword for both name and category\nPlease input your keyword to search.\n");
                        
                        String fKeyword = scanner.nextLine();
                        productManager.searchProducts(fKeyword);
                        break;
                    case "4":
                        System.out.print("Enter Product ID to update: ");
                        String uId = scanner.nextLine();
                        System.out.print("Enter New Name: ");
                        String uName = scanner.nextLine();
                        System.out.print("Enter New Price: ");
                        double uPrice = Double.parseDouble(scanner.nextLine());
                        productManager.updateProduct(uId, uName, uPrice);
                        break;
                    case "5":
                        System.out.print("Enter Product ID to remove: ");
                        String rId = scanner.nextLine();
                        productManager.removeProductById(rId);
                        System.out.println("Product removed successfully.\n");
                        break;
                    case "0":
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

    public static void manageCustomers(CustomerManager customerManager, Scanner scanner) {
        boolean customerRunning = true;
        while (customerRunning) {
            displayCustomerMenu();
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
                        System.out.println("We can search by keyword for customer name\nPlease input your keyword to search.\n");
                        
                        String fKeyword = scanner.nextLine();
                        customerManager.searchCustomer(fKeyword);
                        break;
                    case "4":
                        System.out.print("Enter Customer ID to update: ");
                        String uId = scanner.nextLine();
                        System.out.print("Enter New Name: ");
                        String uName = scanner.nextLine();
                        System.out.print("Enter New Phone: ");
                        String uPhone = scanner.nextLine();
                        customerManager.updateCustomer(uId, uName, uPhone);
                        break;
                    case "5":
                        System.out.print("Enter Customer ID to remove: ");
                        String rId = scanner.nextLine();
                        customerManager.removeCustomerById(rId);
                        break;
                    case "0":
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

    public static void manageOrders(OrderManager orderManager, ProductManager productManager, CustomerManager customerManager, Scanner scanner) {
        boolean orderRunning = true;
        while (orderRunning) {
            displayOrderMenu();
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
                        System.out.println("We can search by order ID for privacy and safe\nPlease ask employees to find more...\nOrder ID: ");
                        String fKeyword = scanner.nextLine();
                        orderManager.searchOrder(fKeyword);
                        break;
                    case "4":
                        System.out.print("Enter Order ID to display details: ");
                        String detailsId = scanner.nextLine();
                        orderManager.displayOrderDetails(detailsId);
                        break;
                    case "5":
                        System.out.print("Enter Order ID to remove: ");
                        String rId = scanner.nextLine();
                        orderManager.removeOrderById(rId);
                        break;
                    case "0":
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
}
