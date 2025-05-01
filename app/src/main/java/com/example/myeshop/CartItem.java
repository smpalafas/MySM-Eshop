package com.example.myeshop;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1; // Αρχική ποσότητα όταν το προϊόν προστεθεί στο καλάθι
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // Μέθοδος για την αύξηση της ποσότητας του προϊόντος
    public void increaseQuantity() {
        quantity++;
    }

    // Μέθοδος για τη μείωση της ποσότητας του προϊόντος
    public void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }

    // Μέθοδος για την αφαίρεση του προϊόντος από το καλάθι αν η ποσότητα είναι 0
    public boolean isEmpty() {
        return quantity == 0;
    }
}
