package com.example.myeshop;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final List<CartItem> cartItems = new ArrayList<>();

    public static void addProduct(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.increaseQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(product));
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
