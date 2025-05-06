package com.example.myeshop;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class Cart {
    private static final List<CartItem> cartItems = new ArrayList<>();

    public static void addProduct(Product product) {
        // Ελέγχουμε αν το προϊόν υπάρχει ήδη στο καλάθι
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.increaseQuantity(); // Αυξάνουμε την ποσότητα
                Log.d("Cart", "Ποσότητα προϊόντος αυξήθηκε: " + item.getProduct().getTitle());
                return;
            }
        }

        // Αν το προϊόν δεν υπάρχει στο καλάθι, το προσθέτουμε με ποσότητα 1
        CartItem newItem = new CartItem(product, 1); // Περάσαμε την ποσότητα 1
        cartItems.add(newItem);
        Log.d("Cart", "Προϊόν προστέθηκε στο καλάθι: " + product.getTitle());
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void removeItem(CartItem item) {
        // Αν η ποσότητα είναι 0, αφαιρούμε το προϊόν από το καλάθι
        if (item.getQuantity() == 0) {
            cartItems.remove(item);
            Log.d("Cart", "Προϊόν αφαιρέθηκε από το καλάθι: " + item.getProduct().getTitle());
        }
    }

    public static void clearCart() {
        cartItems.clear();
    }
}

