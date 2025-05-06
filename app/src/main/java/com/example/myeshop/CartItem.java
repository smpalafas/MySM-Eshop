package com.example.myeshop;
import com.example.myeshop.Product;
public class CartItem {
    private Product product;  // Το προϊόν
    private int quantity;     // Η ποσότητα του προϊόντος

    // Constructor για να δημιουργήσουμε ένα CartItem
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter για το προϊόν
    public Product getProduct() {
        return product;
    }

    // Getter για την ποσότητα
    public int getQuantity() {
        return quantity;
    }

    // Setter για την ποσότητα (αν θέλεις να την ενημερώνεις)
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Μέθοδος για να αυξήσουμε την ποσότητα
    public void increaseQuantity() {
        this.quantity++;
    }

    // Μέθοδος για να μειώσουμε την ποσότητα
    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    // Μέθοδος για να υπολογίσουμε το συνολικό κόστος για αυτό το προϊόν στο καλάθι
    public double getTotal() {
        return product.getPrice() * quantity;
    }
}
