package main;

import controllers.CustomerManager;
import controllers.OrderManager;
import controllers.ProductManager;
import java.util.List;
import java.util.Scanner;
import models.Customer;
import models.Order;
import models.Product;
import utils.FileHandler;
import utils.MockDataUtils;
import views.ConsoleMenu;

public class SaleManagementApp {

  // Initialize global scanner and managers
  private static final Scanner scanner = new Scanner(System.in);
  private static final ProductManager productManager = new ProductManager();
  private static final CustomerManager customerManager = new CustomerManager();
  private static final OrderManager orderManager = new OrderManager();

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    System.out.println("Initializing System...");

    // Attempt to load existing data from binary file
    Object[] loadedData = FileHandler.loadFromFile();

    if (loadedData != null) {
      // Load existing data into managers
      productManager.loadData((List<Product>) loadedData[0]);
      customerManager.loadData((List<Customer>) loadedData[1]);
      orderManager.loadData((List<Order>) loadedData[2]);
    } else {
      // Generate mock data if no file is found
      System.out.println("No binary file found. Generating mock data...");
      MockDataUtils.initializeMockData(
        productManager,
        customerManager,
        orderManager
      );

      // Save initial mock data to file
      FileHandler.saveToFile(
        productManager.getProducts(),
        customerManager.getAllCustomers(),
        orderManager.getOrders()
      );
    }

    // Main application loop
    boolean running = true;
    while (running) {
      ConsoleMenu.displayMainMenu();
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          // Handle product operations
          ConsoleMenu.manageProducts(productManager, scanner);
          break;
        case "2":
          // Handle customer operations
          ConsoleMenu.manageCustomers(customerManager, scanner);
          break;
        case "3":
          // Handle order operations
          ConsoleMenu.manageOrders(
            orderManager,
            productManager,
            customerManager,
            scanner
          );
          break;
        case "4":
          // Display system reports
          ConsoleMenu.showReports(
            orderManager,
            productManager,
            customerManager,
            scanner
          );
          break;
        case "5":
          // Save all current data before shutting down
          System.out.println("Saving data before exiting...");
          FileHandler.saveToFile(
            productManager.getProducts(),
            customerManager.getAllCustomers(),
            orderManager.getOrders()
          );
          System.out.println("Exiting System. Goodbye!");
          running = false;
          break;
        default:
          System.out.println("Invalid option! Please choose again.");
      }
    }
  }
}
