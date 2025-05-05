package com.example.myeshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ProductViewHolder> {

    private final List<Product> products;
    private final Context context;

    public ShopProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        Button addToCartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            priceTextView = itemView.findViewById(R.id.productPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_with_button, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameTextView.setText(product.getTitle());
        holder.priceTextView.setText("€" + product.getPrice());

        holder.addToCartButton.setOnClickListener(v -> {
            Cart.addProduct(product);
            Toast.makeText(context, "Προστέθηκε στο καλάθι", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

