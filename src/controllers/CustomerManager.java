package controllers;

import models.Customer;
import models.Product;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import utils.Validation;

import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    private final List<Customer> customers = new ArrayList<>();

    // // Constructor:
    // public CustomerManager() {
    //     this.customers = new ArrayList<>();
    // }
    //ko can constructor vi da khai bao va khoi tao o tren roi

    //CRUD methods:
    public void addCustomer(Customer customer) throws InvalidInputException {
        if (customer == null) {
            throw new InvalidInputException("Customer data cannot be null!");
        }        

        if (findCustomerById(customer.getCustomerId()) != null) {
            throw new InvalidInputException(
                "Customer ID " + customer.getCustomerId() + " already exists!");
        }

        this.customers.add(customer);
        System.out.println("Customer added successfully.\n");
    }
    //In ra danh sach khach hang
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty.");
            return;
        }
        for (Customer c : customers) {
            System.out.println(c.toString());
        }
    }

    //Tim kiem khach hang theo ID cho find va remove
    public Customer findCustomerById(String customerId) {

        if (customerId == null || customerId.trim().isEmpty()) return null;
        return customers.stream()
                .filter(c -> c.getCustomerId().equalsIgnoreCase(customerId))
                .findFirst().orElse(null);     
    }
    //Tim kiem de in ra man hinh
    public void searchCustomer(String keyword) throws InvalidInputException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidInputException("Search keyword cannot be empty!");
        }

        System.out.println("\n--- SEARCH RESULTS ---");
        boolean found = false;
        
        for (Customer c : customers) {
            // Search only keyword or more...both name & category can find out anf print
            if (c.getCustomerId().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(c.toString());
                found = true;
            }
        }
        //bien linh canh
        if (!found) {
            System.out.println("No customers found matching keyword: '" + keyword + "'");
        }
        System.out.println("----------------------\n");
    }
    //Cap nhat thong tin khach hang (ten va so dien thoai)
    public void updateCustomer(String customerId, String newName, String newPhone)
            throws ItemNotFoundException, InvalidInputException {
        Customer findCustomer = findCustomerById(customerId);
        if (findCustomer == null) {
            throw new ItemNotFoundException(
                "Customer with ID " + customerId + " not found for update!");
        }

        Validation.checkEmptyString(newName, "Customer name");
        Validation.checkEmptyString(newPhone, "Customer phone");
        Validation.checkPhone(newPhone);
        
        findCustomer.setName(newName);
        findCustomer.setPhone(newPhone);
        System.out.println("Customer updated successfully.\n");
    }


    public void removeCustomerById(String customerId)
            throws ItemNotFoundException {
        Customer findCustomer = findCustomerById(customerId);
        if (findCustomer == null) {
            throw new ItemNotFoundException(
                "Customer with ID " + customerId + " not found for removal!");
        }
        customers.remove(findCustomer);
        System.out.println("Customer removed successfully.\n");
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }
}