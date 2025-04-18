package com.example.myeshop;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalTextView;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecyclerView);
        totalTextView = findViewById(R.id.totalAmountTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItems = Cart.getCartItems();
        adapter = new CartAdapter(cartItems, this::updateTotal);
        recyclerView.setAdapter(adapter);

        updateTotal();
    }

    private void updateTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalTextView.setText("Σύνολο: €" + String.format("%.2f", total));
    }
}
