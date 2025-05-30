package com.example.myeshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private OnAddToCartClickListener listener;
    private int[] quantities;

    public interface OnAddToCartClickListener {
        void onAddToCart(Product product, int quantity);
    }

    public ProductAdapter(List<Product> products, OnAddToCartClickListener listener) {
        this.productList = products;
        this.listener = listener;
        this.quantities = new int[products.size()];
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, quantityTextView;
        Button plusButton, minusButton, addToCartButton;

        public ProductViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.productNameTextView);
            priceTextView = v.findViewById(R.id.productPriceTextView);
            quantityTextView = v.findViewById(R.id.quantityTextView);
            plusButton = v.findViewById(R.id.plusButton);
            minusButton = v.findViewById(R.id.minusButton);
            addToCartButton = v.findViewById(R.id.addToCartButton);
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Εμφάνιση του τίτλου και της τιμής
        holder.nameTextView.setText(product.getTitle());
        holder.priceTextView.setText("€" + product.getPrice());

        // Εμφάνιση της τρέχουσας ποσότητας που επιλέχθηκε από τον χρήστη
        holder.quantityTextView.setText(String.valueOf(quantities[position]));  // Εμφανίζει την επιλεγμένη ποσότητα

        // Εμφάνιση της διαθεσιμότητας του προϊόντος
        TextView stockTextView = holder.itemView.findViewById(R.id.stockTextView);
        stockTextView.setText("Διαθεσιμότητα: " + product.getQuantity());  // Εμφάνιση της διαθεσιμότητας

        // Όταν πατάς το κουμπί "+"
        holder.plusButton.setOnClickListener(v -> {
            if (quantities[position] < product.getQuantity()) {  // Ελέγχουμε αν η ποσότητα είναι μικρότερη από τη διαθεσιμότητα
                quantities[position]++;
                holder.quantityTextView.setText(String.valueOf(quantities[position]));  // Ενημέρωση του TextView για την ποσότητα
            } else {
                Toast.makeText(holder.itemView.getContext(), "Δεν υπάρχουν αρκετά προϊόντα σε απόθεμα", Toast.LENGTH_SHORT).show();
            }
        });

        // Όταν πατάς το κουμπί "-"
        holder.minusButton.setOnClickListener(v -> {
            if (quantities[position] > 0) {
                quantities[position]--;
                holder.quantityTextView.setText(String.valueOf(quantities[position]));  // Ενημέρωση του TextView για την ποσότητα
            }
        });

        // Όταν πατάς το κουμπί "Προσθήκη στο καλάθι"
        holder.addToCartButton.setOnClickListener(v -> {
            if (quantities[position] > 0) {
                // Προσθήκη του προϊόντος στο καλάθι
                CartManager.getInstance().addToCart(product, quantities[position]);
                // Ενημέρωση του συνολικού ποσού (αν χρειάζεται)
                updateTotal();
            }
        });
    }



    private void updateTotal() {
        double total = CartManager.getInstance().getTotal();

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}