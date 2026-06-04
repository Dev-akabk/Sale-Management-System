package exceptions;
public class ItemNotFoundException extends Exception {

    // 1. Constructor default with default message to indicate that the item was not found in the system
    public ItemNotFoundException() {
        super("Không tìm thấy đối tượng trong hệ thống!");
    }

    // 2. Constructor with custom message to provide more specific information about the error
    public ItemNotFoundException(String message) {
        super(message);
    }
}