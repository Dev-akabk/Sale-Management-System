package exception;
public class OutOfStockException extends Exception{
    
    public OutOfStockException() {
        //neu vi pham br4,br5 se xuat hien
        //y nghia: giao dich that bai: so luong hang ton kho khong du.
        super("Transaction failed: Insufficient stock available in the inventory.");
    }
   //tu in ra thong bao loi chuan xac o dau de fix ve phia nguoi dung
    public OutOfStockException(String message) {
        super(message);
    }
}