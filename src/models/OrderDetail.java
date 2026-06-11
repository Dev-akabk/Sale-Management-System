package models;
//nhiem vu file nay la luu 1 dong thong tin don hang: san pham + so luong
public class OrderDetail {

    private Product product;
    private int quantity;

    // Constructor--------
    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters & Setters------
    public Product getProduct()              { return product; }
    public void    setProduct(Product p)     { this.product = p; }

    public int     getQuantity()             { return quantity; }
    public void    setQuantity(int quantity) { this.quantity = quantity; }
    
    // Applies product-level polymorphic discount before multiplying by quantity
    public double getLineTotal() {
        double discountedPrice = product.getPrice() - product.calculateDiscount();
        return discountedPrice * quantity;
    }
    //toString-----
    @Override
    public String toString() {
        return "OrderDetail{" + "product=" + product.getProductName() + ", quantity=" + quantity + '}';
    }
}