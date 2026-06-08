package controllers;

import entities.Customer;
import entities.RegularCustomer;
import entities.VIPCustomer;
import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import java.io.*;
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


        foundCustomer.setName(newName);
        foundCustomer.setPhone(newPhone);
        System.out.println("Customer updated successfully.\n");
    }


    public boolean removeCustomerById(String customerId) throws ItemNotFoundException {
        Customer foundCustomer = findCustomerById(customerId);
        if (foundCustomer == null) {
            throw new ItemNotFoundException("Customer with ID " + customerId + " not found for deletion!");
        }
        return customers.remove(foundCustomer);
    }


    public List<Customer> getCustomers() {
        return this.customers;
    }


    public void saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer c : customers) {
                if (c instanceof RegularCustomer) {
                    RegularCustomer rc = (RegularCustomer) c;
                    writer.write(String.format("REGULAR,%s,%s,%s,%s,%.2f,%d",
                            rc.getCustomerId(), rc.getName(), rc.getPhone(), 
                            rc.getAddress(), rc.getTotalSpend(), rc.getLoyaltyPoints()));
                } else if (c instanceof VIPCustomer) {
                    VIPCustomer vc = (VIPCustomer) c;
                    
                    writer.write(String.format("VIP,%s,%s,%s,%s,%.2f,%.4f",
                            vc.getCustomerId(), vc.getName(), vc.getPhone(), 
                            vc.getAddress(), vc.getTotalSpend(), vc.getDiscountRate()));
                }
                writer.newLine();
            }
            System.out.println("Customer records saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving customer data: " + e.getMessage());
        }
    }

    
    public void loadFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        this.customers.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] tokens = line.split(",");
                String type = tokens[0];
                String id = tokens[1];
                String name = tokens[2];
                String phone = tokens[3];
                String address = tokens[4];
                double totalSpend = Double.parseDouble(tokens[5]);

                Customer customer = null;
                if (type.equalsIgnoreCase("REGULAR")) {
                    int loyaltyPoints = Integer.parseInt(tokens[6]);
                    customer = new RegularCustomer(id, name, phone, address, loyaltyPoints);
                } else if (type.equalsIgnoreCase("VIP")) {
                    
                    double discountRate = Double.parseDouble(tokens[6]);
                    customer = new VIPCustomer(id, name, phone, address, discountRate);
                }

                if (customer != null) {
                    customer.setTotalSpend(totalSpend);
                    this.customers.add(customer);
                }
            }
            System.out.println("Customer records loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading customer data: " + e.getMessage());
        }
    }

}