package models;

public class OrderDetail {
    private Product product;
    private int quantity;

    // Constructor
    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters & Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "product=" + product.getProductName() + ", quantity=" + quantity + '}';
    }
}