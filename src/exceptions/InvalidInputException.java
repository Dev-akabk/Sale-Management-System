package exceptions;

public class InvalidInputException extends Exception {

    public InvalidInputException() {
         // neu nhap sai du lieu
        // y nghia: giao dich that bai do nhap sai du lieu dau va0
        super("Invalid input! Please enter valid data.");
    }
         //in ra th0ng ba0 nhap sai du lieu 0 dau de fix
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidInputException(Throwable cause) {
        super(cause);
    }

    public InvalidInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}