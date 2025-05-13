package com.example.myeshop;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);  // Ενημέρωση ποσότητας μέσω του setter
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));  // Προσθήκη νέου προϊόντος
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);  // Αφαίρεση του προϊόντος από το καλάθι
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public double getTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getTotal();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

}
