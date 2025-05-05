package com.example.myeshop;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalTextView;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);

        recyclerView = findViewById(R.id.cartRecyclerView);
        totalTextView = findViewById(R.id.totalAmountTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Λήψη των στοιχείων του καλαθιού
        cartItems = Cart.getCartItems();

        // Αρχικοποίηση Adapter με callback για ενημέρωση συνολικού ποσού
        adapter = new CartAdapter(cartItems, this::updateTotal);
        recyclerView.setAdapter(adapter);

        updateTotal();       // Ενημέρωση συνολικού ποσού αρχικά
        checkIfCartIsEmpty(); // Απόκρυψη λίστας αν το καλάθι είναι άδειο
    }

    // Ενημέρωση του συνολικού ποσού
    private void updateTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalTextView.setText("Σύνολο: €" + String.format("%.2f", total));
    }

    // Έλεγχος αν το καλάθι είναι άδειο
    private void checkIfCartIsEmpty() {
        if (cartItems.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            totalTextView.setVisibility(View.GONE);
            Toast.makeText(this, "Το καλάθι είναι άδειο!", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            totalTextView.setVisibility(View.VISIBLE);
        }
    }
}
