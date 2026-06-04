package controllers;

import entities.Customer;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CustomerList {

    private final List<Customer> customers;

    // Constructor:
    public CustomerList() {
        this.customers = new ArrayList<>();
    }


    public void addCustomer(Customer customer) throws InvalidInputException {
        if (customer == null) {
            throw new InvalidInputException("Customer data cannot be null!");
        }
        
        // Kiểm tra trùng mã khách hàng
        if (findCustomerById(customer.getCustomerId()) != null) {
            throw new InvalidInputException("Customer ID " + customer.getCustomerId() + " already exists!");
        }

        this.customers.add(customer);
        System.out.println("Customer added successfully.\n");
    }

    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty.");
            return;
        }
        for (Customer c : customers) {
            System.out.println(c.toString());
        }
    }


    public Customer findCustomerById(String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(customerId)) {
                return c;
            }
        }
        return null;
    }

    public void updateCustomer(String customerId, String newName, String newPhone) throws ItemNotFoundException, InvalidInputException {
        Customer foundCustomer = findCustomerById(customerId);
        if (foundCustomer == null) {
            throw new ItemNotFoundException("Customer with ID " + customerId + " not found for update!");
        }

        if (newPhone == null || newPhone.trim().isEmpty()) {
            throw new InvalidInputException("New phone number cannot be empty!");
        }

        // Cập nhật dữ liệu bằng setter
        foundCustomer.setName(newName);
        foundCustomer.setPhone(newPhone);
        System.out.println("Customer updated successfully.\n");
    }


    public boolean removeCustomerById(String customerId) throws ItemNotFoundException {
        Customer foundCustomer = findCustomerById(customerId);
        if (foundCustomer == null) {
            throw new ItemNotFoundException(" not found for removal!" + "Customer with ID " + customerId);
        }
        return customers.remove(foundCustomer);
    }

    // Hàm lấy toàn bộ danh sách khách hàng
    public List<Customer> getCustomers() {
        return this.customers;
    }
}