package utils;

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

public class Validation {
    // id cua customer se bat dau bang 'C' va theo sau la 3 chu so (VD: C_001 C_002,...)
    private static final String CUSTOMER_ID_REGEX = "^C_\\d{3}$";
    // id cua product se bat dau bang 'P' va theo sau la 3 chu so (VD: P_001, P_002,...)
    private static final String PRODUCT_ID_REGEX = "^P_\\d{3}$";
    // id cua order se bat dau bang 'O' va theo sau la 3 chu so (VD: O_001, O_002,...)
    private static final String ORDER_ID_REGEX = "^O_\\d{3}$";
    //chi ap dung sdt bat dau bang 0 tong co 10 chu so (VD: 0123456789, 0987654321,...)
    private static final String PHONE_NUMBER_REGEX = "^0\\d{9}$"; // la 1 so 0 cong voi 9 so 
    // email co dang la: 1 chuoi ky tu + @ + 1 chuoi ky tu + . + 1 chuoi ky tu (VD: john.doe@example.com)
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.\\w+$";

    //_____________________________
    //CAC HAM KIEM TRA DU LIEU STRING
    //_____________________________

    //kiem tra chuoi co rong hay khong
    public static void checkEmptyString(String input, String fieldName) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty!");
        }
    }
    //gioi han do dai chuoi
    public static void checkStringLength(String input, int minLength, int maxLength, String fieldName) throws InvalidInputException {
        if (input.length() < minLength || input.length() > maxLength) {
            throw new InvalidInputException(fieldName + " must be between " + minLength + " and " + maxLength + " characters long!");
        }
    }
    //kiem tra chuoi khong cho phep chua ky tu dac biet
    public static void checkNoSpecialCharacters(String input, String fieldName) throws InvalidInputException {
        if (!input.matches("^[a-zA-Z0-9 ]+$")) {
            throw new InvalidInputException(fieldName + " cannot contain special characters!");
        }
    }
    //______________________________
    //CAC HAM KIEM TRA DINH DANG(REGEX)
    //_______________________________

    public static void checkCustomerIdFormat(String customerId) throws InvalidInputException {
        if (!customerId.matches(CUSTOMER_ID_REGEX)) {
            throw new InvalidInputException("Customer ID must follow the format: C_XXX (e.g., C_001)!");
        }
    }
    public static void checkProductIdFormat(String productId) throws InvalidInputException {
        if (!productId.matches(PRODUCT_ID_REGEX)) {
            throw new InvalidInputException("Product ID must follow the format: P_XXX (e.g., P_001)!");
        }
    }
    public static void checkOrderIdFormat(String orderId) throws InvalidInputException {
        if (!orderId.matches(ORDER_ID_REGEX)) {
            throw new InvalidInputException("Order ID must follow the format: O_XXX (e.g., O_001)!");
        }
    }
    public static void checkPhone(String phone) throws InvalidInputException {
        if (!phone.matches(PHONE_NUMBER_REGEX)) {
            throw new InvalidInputException("Phone number must follow the format: 0XXXXXXXXX (e.g., 0123456789)!");
        }
    }
    public static void checkEmail(String email) throws InvalidInputException {
        if (!email.matches(EMAIL_REGEX)) {
            throw new InvalidInputException("Email must follow the format: username@domain.extension!");
        }
    }
    //_______________________________
    //CAC HAM KIEM TRA DU LIEU SO
    //_______________________________
    public static void checkPositiveDouble(double value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be greater than 0!");
        }
    }
    public static void checkPositiveInt(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be greater than 0!");
        }
    }
    public static void checkNonNegativeInt(int value, String fieldName) throws InvalidInputException {
        if (value < 0) {
            throw new InvalidInputException(fieldName + " cannot be negative!");
        }
    }
    public static void checkNonNegativeDouble(double value, String fieldName) throws InvalidInputException {
        if (value < 0) {
            throw new InvalidInputException(fieldName + " cannot be negative!");
        }
    }
    
}