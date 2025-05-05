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

        holder.nameTextView.setText(product.getTitle());
        holder.priceTextView.setText("€" + product.getPrice());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        holder.plusButton.setOnClickListener(v -> {
            item.increaseQuantity();
            onCartChanged.run();
            notifyDataSetChanged();
        });

        holder.minusButton.setOnClickListener(v -> {
            item.decreaseQuantity();
            if (item.getQuantity() <= 0) {
                Cart.removeItem(item);
            }
            onCartChanged.run();
            notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
