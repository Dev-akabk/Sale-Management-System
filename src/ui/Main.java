<<<<<<< HEAD

package ui;

public class main {
    
}
=======
package UI;

import controllers.ProductList;
import entity.Product;

public class Main {

    public static void main(String[] args) {

        ProductList manager = new ProductList();

        System.out.println("Testing Empty List: ");
        manager.displayAllProducts();
        System.out.println();

        Product p1 = new Product("P001", "Laptop", "Lenovo", (int) 20, (double) 199.99);
        Product p2 = new Product("P002", "Smartphone", "Samsung", (int) 30, (double) 988.00);
        Product p3 = new Product("P003", "Keyboard", "Razer", (int) 50, (double) 249.99);

        System.out.println("Adding Products: ");
        manager.addProduct(p1);
        manager.addProduct(p2);
        manager.addProduct(p3);
        manager.addProduct(null);

        System.out.println("Displaying All Products: ");
        manager.displayAllProducts();
        System.out.println();

        System.out.println("Finding a Product by ID: ");
        String searchId = "p002";
        Product found = manager.findProductById(searchId);
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Product with ID " + searchId + " not found.");
        }
        System.out.println();

        System.out.println("Removing a Product: ");
        String removeId = "P001";
        boolean isRemoved = manager.removeProductById(removeId);
        if (isRemoved) {
            System.out.println("Successfully removed product: " + removeId);
        } else {
            System.out.println("Failed to remove product: " + removeId);
        }
        System.out.println();

        System.out.println("Final Product List: ");
        manager.displayAllProducts();
    }
}
>>>>>>> 56b495c24438953d1f05fd09160ec6be9451d5cd
