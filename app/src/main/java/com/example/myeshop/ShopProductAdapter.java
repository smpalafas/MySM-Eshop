package com.example.myeshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ProductViewHolder> {

    private final List<Product> products;
    private final Context context;
    private static final String TAG = "ShopProductAdapter";

    public ShopProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        Button addToCartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            // Προσπαθούμε να βρούμε τα views αλλά ελέγχουμε αν υπάρχουν
            try {
                nameTextView = itemView.findViewById(R.id.productName);
                priceTextView = itemView.findViewById(R.id.productPrice);
                addToCartButton = itemView.findViewById(R.id.addToCartButton);
            } catch (Exception e) {
                Log.e("ProductViewHolder", "Σφάλμα εύρεσης views: " + e.getMessage());
            }
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product_with_button, parent, false);
            return new ProductViewHolder(view);
        } catch (Exception e) {
            Log.e(TAG, "Error inflating view: " + e.getMessage());
            // Δημιουργία εναλλακτικής προβολής σε περίπτωση σφάλματος
            LinearLayout fallbackView = new LinearLayout(parent.getContext());
            fallbackView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ProductViewHolder(fallbackView);
        }
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        try {
            if (products != null && position < products.size()) {
                Product product = products.get(position);

                // Έλεγχος για null views
                if (holder.nameTextView != null && product.getTitle() != null) {
                    holder.nameTextView.setText(product.getTitle());
                }

                if (holder.priceTextView != null) {
                    holder.priceTextView.setText("€" + product.getPrice());
                }

                if (holder.addToCartButton != null) {
                    holder.addToCartButton.setOnClickListener(v -> {
                        try {
                            Cart.addProduct(product);
                            Toast.makeText(context, "Προστέθηκε στο καλάθι: " + product.getTitle(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "Error adding to cart: " + e.getMessage());
                            Toast.makeText(context, "Σφάλμα κατά την προσθήκη στο καλάθι", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Προσπάθεια προσπέλασης μη έγκυρης θέσης στη λίστα προϊόντων");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error binding view holder: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }
}