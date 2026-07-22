package controllers;

import exceptions.InvalidInputException;
import exceptions.ItemNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import models.Customer;
import models.Product;
import utils.Validation;

public class CustomerManager {

  //leak encapsulation: data can be modified outside => unmodifiable list
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
        "Customer ID " + customer.getCustomerId() + " already exists!"
      );
    }

    this.customers.add(customer);
    // Success messaging delegated to view layer
  }

  //Tim kiem khach hang theo ID cho find va remove
  public Customer findCustomerById(String customerId) {
    if (customerId == null || customerId.trim().isEmpty()) return null;
    return customers
      .stream()
      .filter(c -> c.getCustomerId().equalsIgnoreCase(customerId))
      .findFirst()
      .orElse(null);
  }

  /**
   * Stream-based keyword search over customer ID.
   * Returns a filtered list for the view layer to display.
   *
   * @param keyword  search term (matched case-insensitively against customer ID)
   * @return list of matching customers (empty list if none found)
   * @throws InvalidInputException if keyword is null or blank
   */
  public List<Customer> searchCustomer(String keyword)
    throws InvalidInputException {
    if (keyword == null || keyword.trim().isEmpty()) {
      throw new InvalidInputException("Search keyword cannot be empty!");
    }
    String lowerKey = keyword.toLowerCase().trim();
    return customers
      .stream()
      .filter(c -> c.getCustomerId().toLowerCase().contains(lowerKey))
      .collect(Collectors.toList());
  }

  //Cap nhat thong tin khach hang (ten va so dien thoai)
  public void updateCustomer(String customerId, String newName, String newPhone)
    throws ItemNotFoundException, InvalidInputException {
    Customer findCustomer = findCustomerById(customerId);
    if (findCustomer == null) {
      throw new ItemNotFoundException(
        "Customer with ID " + customerId + " not found for update!"
      );
    }

    Validation.checkEmptyString(newName, "Customer name");
    Validation.checkEmptyString(newPhone, "Customer phone");
    Validation.checkPhone(newPhone);

    findCustomer.setName(newName);
    findCustomer.setPhone(newPhone);
    // Success messaging delegated to view layer
  }

  public void removeCustomerById(String customerId)
    throws ItemNotFoundException {
    Customer findCustomer = findCustomerById(customerId);
    if (findCustomer == null) {
      throw new ItemNotFoundException(
        "Customer with ID " + customerId + " not found for removal!"
      );
    }
    customers.remove(findCustomer);
    // Success messaging delegated to view layer
  }

  public List<Customer> getAllCustomers() {
    return Collections.unmodifiableList(this.customers);
  }

  public void loadData(List<Customer> loadedCustomers) {
    this.customers.clear();
    this.customers.addAll(loadedCustomers);
  }
}
