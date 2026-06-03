package controllers;

import entity.Customer;
import exception.InvalidInputException;
import exception.ItemNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CustomerList {

    private final List<Customer> customers;

    // Constructor:
    public CustomerList() {
        this.customers = new ArrayList<>();
    }

    // =========================================================================
    // Begin
    //   Step 1: Nhận đối tượng customer từ giao diện (UI).
    //   Step 2: Nếu customer bị null -> Quăng InvalidInputException.
    //   Step 3: Kiểm tra xem mã khách hàng (ID) đã tồn tại trong danh sách chưa. Nếu trùng -> Quăng InvalidInputException.
    //   Step 4: Nếu hợp lệ, thêm khách hàng vào danh sách 'customers'.
    // End
    // =========================================================================
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

    // =========================================================================
    // Begin
    //   Step 1: Kiểm tra xem danh sách khách hàng có rỗng không.
    //   Step 2: Nếu rỗng, thông báo cho người dùng biết.
    //   Step 3: Nếu có dữ liệu, dùng vòng lặp để in ra thông tin từng khách hàng.
    // End
    // =========================================================================
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty.");
            return;
        }
        for (Customer c : customers) {
            System.out.println(c.toString());
        }
    }

    // =========================================================================
    // Begin
    //   Step 1: Nhận vào chuỗi ID khách hàng cần tìm.
    //   Step 2: Duyệt qua từng khách hàng trong danh sách 'customers'.
    //   Step 3: So sánh mã khách hàng, nếu trùng khớp thì trả về đối tượng Customer đó.
    //   Step 4: Nếu không tìm thấy khách hàng nào khớp, trả về null.
    // End
    // =========================================================================
    public Customer findCustomerById(String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(customerId)) {
                return c;
            }
        }
        return null;
    }

    // =========================================================================
    // Begin
    //   Step 1: Nhận vào mã khách hàng cần sửa và thông tin mới (Tên mới, Sđt mới).
    //   Step 2: Tìm kiếm khách hàng theo ID. Nếu không thấy -> Quăng ItemNotFoundException.
    //   Step 3: Nếu tìm thấy, kiểm tra số điện thoại mới có bị trống không.
    //   Step 4: Gọi các hàm setter để cập nhật lại tên và số điện thoại mới.
    // End
    // =========================================================================
    public void updateCustomer(String customerId, String newName, String newPhone) throws ItemNotFoundException, InvalidInputException {
        Customer foundCustomer = findCustomerById(customerId);
        if (foundCustomer == null) {
            throw new ItemNotFoundException("Customer with ID " + customerId + " not found for update!");
        }

        if (newPhone == null || newPhone.trim().isEmpty()) {
            throw new InvalidInputException("New phone number cannot be empty!");
        }

        // Cập nhật dữ liệu bằng setter
        foundCustomer.setCustomerName(newName);
        foundCustomer.setPhone(newPhone);
        System.out.println("Customer updated successfully.\n");
    }

    // =========================================================================
    // Begin
    //   Step 1: Nhận vào mã khách hàng cần xóa.
    //   Step 2: Tìm kiếm khách hàng đó trong danh sách.
    //   Step 3: Nếu không tìm thấy -> Quăng ItemNotFoundException.
    //   Step 4: Nếu tìm thấy, xóa khách hàng đó khỏi danh sách 'customers'.
    // End
    // =========================================================================
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