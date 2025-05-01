package com.example.myeshop;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItem> cartItems;
    private final Runnable onCartChanged;

    public CartAdapter(List<CartItem> cartItems, Runnable onCartChanged) {
        this.cartItems = cartItems;
        this.onCartChanged = onCartChanged;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, quantityTextView;
        Button minusButton, plusButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.cartItemName);
            priceTextView = itemView.findViewById(R.id.cartItemPrice);
            quantityTextView = itemView.findViewById(R.id.cartItemQuantity);
            minusButton = itemView.findViewById(R.id.cartItemMinus);
            plusButton = itemView.findViewById(R.id.cartItemPlus);
        }
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        Product product = item.getProduct();

        // Ενημέρωση του UI με τα δεδομένα του προϊόντος
        holder.nameTextView.setText(product.getTitle());
        holder.priceTextView.setText("€" + product.getPrice());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        // Ενέργεια για το κουμπί "+" (Αύξηση ποσότητας)
        holder.plusButton.setOnClickListener(v -> {
            item.increaseQuantity();  // Αύξηση της ποσότητας
            notifyItemChanged(position);  // Ενημέρωση της θέσης στο RecyclerView
            onCartChanged.run();  // Ενημέρωση του συνολικού ποσού
        });

        // Ενέργεια για το κουμπί "-" (Μείωση ποσότητας)
        holder.minusButton.setOnClickListener(v -> {
            item.decreaseQuantity();  // Μείωση της ποσότητας
            if (item.getQuantity() == 0) {
                // Αν η ποσότητα γίνει 0, αφαιρούμε το προϊόν από το καλάθι
                Cart.removeItem(item);  // Αφαίρεση του προϊόντος από τη λίστα Cart
                cartItems.remove(position);  // Αφαίρεση του προϊόντος από τη λίστα RecyclerView
                notifyItemRemoved(position);  // Ενημέρωση του RecyclerView για την αφαίρεση
                notifyItemRangeChanged(position, getItemCount());  // Ενημέρωση των υπολοίπων στοιχείων
            } else {
                // Αν η ποσότητα είναι μεγαλύτερη από 0, ενημερώνουμε το UI για την αλλαγή
                notifyItemChanged(position);  // Ενημέρωση της θέσης στο RecyclerView
            }
            onCartChanged.run();  // Ενημέρωση του συνολικού ποσού
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
