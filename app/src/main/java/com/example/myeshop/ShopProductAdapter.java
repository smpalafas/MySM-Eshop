package com.example.myeshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ProductViewHolder> {

    private final List<Product> productList;
    private final Context context;

    public ShopProductAdapter(List<Product> products, Context context) {
        this.productList = products;
        this.context = context;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView, priceTextView, quantityTextView;
        Button addToCartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            descriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            quantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }

    @Override
    public ShopProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_with_button, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getTitle());
        holder.descriptionTextView.setText(product.getDescription());
        holder.priceTextView.setText("€" + product.getPrice());
        holder.quantityTextView.setText("Διαθέσιμο: " + product.getQuantity());

        holder.addToCartButton.setOnClickListener(v -> {
            Cart.addProduct(product); // Προσθήκη προϊόντος στο καλάθι
            Toast.makeText(context, "Προστέθηκε στο καλάθι!", Toast.LENGTH_SHORT).show(); // Ειδοποίηση χρήστη
            // Ενημέρωση του RecyclerView ή άλλων UI στοιχείων εάν χρειάζεται
            notifyDataSetChanged(); // Ενημερώνουμε το UI αν χρειάζεται
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
