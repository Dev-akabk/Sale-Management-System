<<<<<<< HEAD:src/exceptions/ItemNotFoundException.java
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
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author LENOVO
 */
public class ItemNotFoundException {
    
}
>>>>>>> 56b495c24438953d1f05fd09160ec6be9451d5cd:src/exception/ItemNotFoundException.java
