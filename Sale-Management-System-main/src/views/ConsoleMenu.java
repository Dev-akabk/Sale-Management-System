package views;

import controllers.CustomerManager;
import controllers.OrderManager;
import controllers.ProductManager;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import exceptions.OutOfStockException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import models.Accessories;
import models.Customer;
import models.Electronics;
import models.Order;
import models.OrderDetail;
import models.Product;
import models.RegularCustomer;
import models.VIPCustomer;

public class ConsoleMenu {

  // =========================================================================
  // MAIN MENU
  // =========================================================================
  public static void displayMainMenu() {
    System.out.println("\n====================================");
    System.out.println("      SALE MANAGEMENT SYSTEM        ");
    System.out.println("====================================");
    System.out.println("1. Product Management");
    System.out.println("2. Customer Management");
    System.out.println("3. Order Management");
    System.out.println("4. Reports");
    System.out.println("5. Exit");
    System.out.println("====================================");
    System.out.print("Please choose an option (1-5): ");
  }

  // =========================================================================
  // SUB-MENUS
  // =========================================================================
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
    System.out.println("3. Search Order");
    System.out.println("4. Display Order Details");
    System.out.println("5. Remove Order");
    System.out.println("6. Finalize / Checkout Order");
    System.out.println("0. Back to Main Menu");
    System.out.print("Please choose an option (0-6): ");
  }

  public static void displayReportsMenu() {
    System.out.println("\n--- REPORTS ---");
    System.out.println("1. Best-Selling Products (by quantity sold)");
    System.out.println("2. Top Customers (by total spend)");
    System.out.println("0. Back to Main Menu");
    System.out.print("Please choose an option (0-2): ");
  }

  // =========================================================================
  // PRODUCT MANAGEMENT
  // =========================================================================
  public static void manageProducts(
    ProductManager productManager,
    Scanner scanner
  ) {
    boolean productRunning = true;
    while (productRunning) {
      displayProductMenu();
      String choice = scanner.nextLine().trim();

      try {
        switch (choice) {
          case "1":
            System.out.print(
              "Select Product Type (1 - Electronics, 2 - Accessories): "
            );
            String type = scanner.nextLine().trim();

            System.out.print("Enter ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Category: ");
            String category = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Stock Quantity: ");
            int stock = Integer.parseInt(scanner.nextLine());

            Product newProduct;

            if ("1".equals(type)) {
              System.out.print("Enter Warranty (months): ");
              int warranty = Integer.parseInt(scanner.nextLine());
              newProduct = new Electronics(
                id,
                name,
                category,
                price,
                stock,
                warranty
              );
            } else if ("2".equals(type)) {
              System.out.print("Enter Size: ");
              String size = scanner.nextLine();
              newProduct = new Accessories(
                id,
                name,
                category,
                price,
                stock,
                size
              );
            } else {
              System.out.println(
                "Invalid selection! Defaulting to standard Product."
              );
              newProduct = new Product(id, name, category, price, stock);
            }
            productManager.addProduct(newProduct);
            System.out.println("Product added successfully!");
            break;
          case "2":
            displayProductList(productManager.getProducts());
            break;
          case "3":
            System.out.println(
              "Search by keyword (matches product name or category):"
            );
            String fKeyword = scanner.nextLine();
            List<Product> results = productManager.searchProducts(fKeyword);
            System.out.println("\n--- SEARCH RESULTS ---");
            if (results.isEmpty()) {
              System.out.println(
                "No products found matching keyword: '" + fKeyword + "'"
              );
            } else {
              results.forEach(p -> System.out.println(p.toString()));
            }
            System.out.println("----------------------\n");
            break;
          case "4":
            System.out.print("Enter Product ID to update: ");
            String uId = scanner.nextLine();
            System.out.print("Enter New Name: ");
            String uName = scanner.nextLine();
            System.out.print("Enter New Price: ");
            double uPrice = Double.parseDouble(scanner.nextLine());
            productManager.updateProduct(uId, uName, uPrice);
            System.out.println("Product updated successfully.\n");
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

  // Display helper for the product list — pure View concern, no business logic.
  private static void displayProductList(List<Product> products) {
    if (products.isEmpty()) {
      System.out.println("The product list is empty.");
      return;
    }
    for (Product p : products) {
      System.out.println(p.toString());
    }
  }

  // =========================================================================
  // CUSTOMER MANAGEMENT
  // =========================================================================
  public static void manageCustomers(
    CustomerManager customerManager,
    Scanner scanner
  ) {
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
              customerManager.addCustomer(
                new VIPCustomer(id, name, phone, address, rate)
              );
            } else {
              System.out.print("Enter Loyalty Points: ");
              int points = Integer.parseInt(scanner.nextLine());
              customerManager.addCustomer(
                new RegularCustomer(id, name, phone, address, points)
              );
            }
            System.out.println("Customer added successfully.\n");
            break;
          case "2":
            displayCustomerList(customerManager.getAllCustomers());
            break;
          case "3":
            System.out.println("Search customer by ID keyword:");
            String fKeyword = scanner.nextLine();
            List<Customer> cResults = customerManager.searchCustomer(fKeyword);
            System.out.println("\n--- SEARCH RESULTS ---");
            if (cResults.isEmpty()) {
              System.out.println(
                "No customers found matching keyword: '" + fKeyword + "'"
              );
            } else {
              cResults.forEach(c -> System.out.println(c.toString()));
            }
            System.out.println("----------------------\n");
            break;
          case "4":
            System.out.print("Enter Customer ID to update: ");
            String uId = scanner.nextLine();
            System.out.print("Enter New Name: ");
            String uName = scanner.nextLine();
            System.out.print("Enter New Phone: ");
            String uPhone = scanner.nextLine();
            customerManager.updateCustomer(uId, uName, uPhone);
            System.out.println("Customer updated successfully.\n");
            break;
          case "5":
            System.out.print("Enter Customer ID to remove: ");
            String rId = scanner.nextLine();
            customerManager.removeCustomerById(rId);
            System.out.println("Customer removed successfully.\n");
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

  // Display helper for the customer list — pure View concern, no business logic.
  private static void displayCustomerList(List<Customer> customers) {
    if (customers.isEmpty()) {
      System.out.println("The customer list is empty.");
      return;
    }
    for (Customer c : customers) {
      System.out.println(c.toString());
    }
  }

  // =========================================================================
  // ORDER MANAGEMENT
  // =========================================================================
  public static void manageOrders(
    OrderManager orderManager,
    ProductManager productManager,
    CustomerManager customerManager,
    Scanner scanner
  ) {
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
              System.out.println("Order created successfully.\n");
            }
            break;
          case "2":
            System.out.print("Enter Order ID: ");
            String targetOId = scanner.nextLine();
            System.out.print("Enter Product ID: ");
            String pId = scanner.nextLine();
            System.out.print("Enter Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());

            // BR8: quantity must be >= 1 (validated downstream by checkPositiveInt)
            Product product = productManager.findProductById(pId);
            if (product == null) {
              System.out.println("ERROR: Product not found!");
            } else {
              orderManager.addProductToOrder(targetOId, product, qty);
              System.out.println("Product added to order successfully.\n");
            }
            break;
          case "3":
            System.out.print("Enter Order ID keyword to search: ");
            String fKeyword = scanner.nextLine();
            List<Order> oResults = orderManager.searchOrder(fKeyword);
            System.out.println("\n--- SEARCH RESULTS ---");
            if (oResults.isEmpty()) {
              System.out.println(
                "No orders found matching keyword: '" + fKeyword + "'"
              );
            } else {
              oResults.forEach(o -> System.out.println(o.toString()));
            }
            System.out.println("----------------------\n");
            break;
          case "4":
            System.out.print("Enter Order ID to display details: ");
            String detailsId = scanner.nextLine();
            // BR8: warn if order has no items before displaying
            Order targetOrder = orderManager.findOrderById(detailsId);
            if (targetOrder == null) {
              System.out.println("ERROR: Order not found!");
            } else if (targetOrder.getOrderDetails().isEmpty()) {
              System.out.println(
                "WARNING (BR8): Order '" +
                  detailsId +
                  "' has no items. Please add at least one product before finalizing."
              );
            } else {
              displayOrderDetails(targetOrder);
            }
            break;
          case "5":
            System.out.print("Enter Order ID to remove: ");
            String rId = scanner.nextLine();
            orderManager.removeOrderById(rId);
            System.out.println("Order removed successfully.\n");
            break;
          case "6":
            System.out.print("Enter Order ID to finalize/checkout: ");
            String checkoutId = scanner.nextLine();
            orderManager.checkoutOrder(checkoutId);
            System.out.println(
              "Order finalized successfully. Customer's total spend updated.\n"
            );
            break;
          case "0":
            orderRunning = false;
            break;
          default:
            System.out.println("Invalid option! Please choose again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("ERROR: Invalid number format!");
      } catch (
        InvalidInputException
        | ItemNotFoundException
        | OutOfStockException e
      ) {
        System.out.println("ERROR: " + e.getMessage());
      }
    }
  }

  // =========================================================================
  // displayOrderDetails — shows the full item breakdown for one order.
  // Pure View concern: formatting/printing belongs here, not in the Controller.
  // =========================================================================
  private static void displayOrderDetails(Order order) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    System.out.println("=========================================");
    System.out.println("           ORDER DETAILS: " + order.getOrderId());
    System.out.println("=========================================");
    System.out.println("Customer : " + order.getCustomer().getName());
    System.out.println("Date     : " + dtf.format(order.getOrderDate()));
    System.out.println("-----------------------------------------");
    System.out.printf(
      "%-20s | %-5s | %-10s%n",
      "Product Name",
      "Qty",
      "Subtotal"
    );
    System.out.println("-----------------------------------------");

    for (OrderDetail detail : order.getOrderDetails()) {
      double subTotal = detail.getProduct().getPrice() * detail.getQuantity();
      System.out.printf(
        "%-20s | %-5d | $%.2f%n",
        detail.getProduct().getProductName(),
        detail.getQuantity(),
        subTotal
      );
    }

    System.out.println("-----------------------------------------");
    System.out.printf("FINAL TOTAL: $%.2f%n", order.getTotalAmount());
    System.out.println("=========================================\n");
  }

  // =========================================================================
  // REPORTS
  // =========================================================================
  public static void showReports(
    OrderManager orderManager,
    ProductManager productManager,
    CustomerManager customerManager,
    Scanner scanner
  ) {
    boolean reportsRunning = true;
    while (reportsRunning) {
      displayReportsMenu();
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          showBestSellingProducts(orderManager);
          break;
        case "2":
          showTopCustomers(customerManager);
          break;
        case "0":
          reportsRunning = false;
          break;
        default:
          System.out.println("Invalid option! Please choose again.");
      }
    }
  }

  /**
   * Best-Selling Products report: aggregates total quantity sold per product
   * across all orders using Java Streams, then prints sorted descending by qty.
   */
  private static void showBestSellingProducts(OrderManager orderManager) {
    System.out.println("\n========================================");
    System.out.println("        BEST-SELLING PRODUCTS           ");
    System.out.println("========================================");

    // Flatten all order details, group by product name, sum quantities
    Map<String, Integer> salesMap = new HashMap<>();
    for (Order order : orderManager.getOrders()) {
      for (OrderDetail detail : order.getOrderDetails()) {
        String productName = detail.getProduct().getProductName();
        salesMap.merge(productName, detail.getQuantity(), Integer::sum);
      }
    }

    if (salesMap.isEmpty()) {
      System.out.println("No sales data available yet.");
    } else {
      System.out.printf("%-30s | %-12s%n", "Product Name", "Total Sold");
      System.out.println("----------------------------------------");
      salesMap
        .entrySet()
        .stream()
        .sorted(
          Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
        )
        .forEach(e ->
          System.out.printf("%-30s | %d units%n", e.getKey(), e.getValue())
        );
    }
    System.out.println("========================================\n");
  }

  /**
   * Top Customers report: sorts customers by totalSpend descending using Streams.
   */
  private static void showTopCustomers(CustomerManager customerManager) {
    System.out.println("\n========================================");
    System.out.println("           TOP CUSTOMERS                ");
    System.out.println("========================================");

    List<Customer> sorted = customerManager
      .getAllCustomers()
      .stream()
      .sorted(Comparator.comparingDouble(Customer::getTotalSpend).reversed())
      .collect(Collectors.toList());

    if (sorted.isEmpty()) {
      System.out.println("No customer data available yet.");
    } else {
      System.out.printf(
        "%-6s | %-20s | %-10s%n",
        "Rank",
        "Customer Name",
        "Total Spend"
      );
      System.out.println("----------------------------------------");
      int rank = 1;
      for (Customer c : sorted) {
        System.out.printf(
          "%-6d | %-20s | %.2f VND%n",
          rank++,
          c.getName(),
          c.getTotalSpend()
        );
      }
    }
    System.out.println("========================================\n");
  }
}
