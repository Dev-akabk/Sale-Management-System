package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import models.Customer;
import models.Order;
import models.Product;

public class FileHandler {

  private static final String DATA_FILE = "system_data.dat";

  // 1. Hàm lưu dữ liệu xuống File nhị phân (nhận data từ các Manager thông qua tham số)
  public static void saveToFile(
    List<Product> products,
    List<Customer> customers,
    List<Order> orders
  ) {
    File file = new File(DATA_FILE);
    // Sử dụng try-with-resources để tự động đóng luồng dữ liệu
    try (
      FileOutputStream fos = new FileOutputStream(file);
      ObjectOutputStream oos = new ObjectOutputStream(fos)
    ) {
      oos.writeObject(products);
      oos.writeObject(customers);
      oos.writeObject(orders);
      System.out.println("Data list successfully saved to file!");
    } catch (Exception e) {
      System.out.println("Error saving file: " + e.getMessage() + " (Fail)");
    }
  }

  // 2. Hàm đọc dữ liệu từ File nhị phân và trả về một mảng chứa 3 list
  @SuppressWarnings("unchecked")
  public static Object[] loadFromFile() {
    File file = new File(DATA_FILE);
    // Kiểm tra nếu file chưa tồn tại (lần đầu chạy hệ thống) thì bỏ qua và trả về null
    if (!file.exists()) {
      System.out.println(
        "No historical data available. Initializing new list."
      );
      return null;
    }

    try (
      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream ois = new ObjectInputStream(fis)
    ) {
      // Đọc object và ép kiểu về List
      List<Product> productList = (List<Product>) ois.readObject();
      List<Customer> customerList = (List<Customer>) ois.readObject();
      List<Order> orderList = (List<Order>) ois.readObject();

      System.out.println("Data successfully loaded from file!");

      // Trả về mảng chứa 3 danh sách để Manager nạp vào
      return new Object[] { productList, customerList, orderList };
    } catch (Exception e) {
      System.out.println("Error reading file: " + e.getMessage() + " (Fail)");
      return null;
    }
  }
}
