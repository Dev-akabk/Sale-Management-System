package views;

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
        System.out.println("3. Update Product");
        System.out.println("4. Remove Product");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please choose an option (1-5): ");
    }

    public static void displayCustomerMenu() {
        System.out.println("\n--- CUSTOMER MANAGEMENT ---");
        System.out.println("1. Add Customer");
        System.out.println("2. Display All Customers");
        System.out.println("3. Update Customer");
        System.out.println("4. Remove Customer");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please choose an option (1-5): ");
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
}
