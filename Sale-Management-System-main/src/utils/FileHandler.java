package utils;

import models.Customer;
import models.Order;
import models.Product;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private List<Product> productList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();
    private static final String DATA_FILE = "system_data.dat";

    public void saveToFile() {
        File file = new File(DATA_FILE);
        // Sử dụng try-with-resources để tự động đóng luồng dữ liệu
        try ( FileOutputStream fos = new FileOutputStream(file);  ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(productList);
            oos.writeObject(customerList);
            oos.writeObject(orderList);
            System.out.println("Data list successfully saved to file!");

        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage() + " (Fail)");
        }
    }

    // 2. Hàm đọc dữ liệu từ File nhị phân
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(DATA_FILE);
        // Kiểm tra nếu file chưa tồn tại (lần đầu chạy hệ thống) thì bỏ qua
        if (!file.exists()) {
            System.out.println("No historical data available. Initializing new list.");
            return;
        }

        try ( FileInputStream fis = new FileInputStream(file);  ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Đọc object và ép kiểu về List
            productList = (List<Product>) ois.readObject();
            customerList = (List<Customer>) ois.readObject();
            orderList = (List<Order>) ois.readObject();
            System.out.println("Data successfully loaded from file!");

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage() + " (Fail)");
        }
    }
}
