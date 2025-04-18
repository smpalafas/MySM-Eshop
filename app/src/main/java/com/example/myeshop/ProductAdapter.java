package com.example.myeshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, priceTextView;

        public ProductViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.productNameTextView);
            priceTextView = v.findViewById(R.id.productPriceTextView);
        }
    }

    public ProductAdapter(List<Product> products) {
        productList = products;
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getTitle());
        holder.priceTextView.setText("€" + product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
