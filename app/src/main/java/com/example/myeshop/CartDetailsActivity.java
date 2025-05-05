package com.example.myeshop;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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

        // Προσθήκη του back button στο action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Το καλάθι μου");
        }

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

    // Χειρισμός του back button στο action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}