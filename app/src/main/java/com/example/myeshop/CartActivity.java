package com.example.myeshop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;  // Εισαγωγή για το MenuItem
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;



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

        // Φορτώνουμε τα προϊόντα στο καλάθι
        cartItems = Cart.getCartItems();
        adapter = new CartAdapter(cartItems, this::updateTotal);
        recyclerView.setAdapter(adapter);

        updateTotal();  // Ενημέρωση του συνολικού ποσού αρχικά
        checkIfCartIsEmpty();  // Ελέγχουμε αν το καλάθι είναι άδειο

        // Έλεγχος για το κουμπί "Προσθήκη στο καλάθι"
        Button addButton = findViewById(R.id.addToCartButton);  // Το ID του κουμπιού "Προσθήκη στο καλάθι"
        addButton.setOnClickListener(v -> {
            // Δημιουργούμε ένα νέο προϊόν και το προσθέτουμε στο καλάθι
            Product product = new Product(1, "Όνομα Προϊόντος", "Περιγραφή προϊόντος", 10.0, "1", 101);
            Cart.addProduct(product);  // Προσθήκη του προϊόντος στο καλάθι
            cartItems = Cart.getCartItems();  // Ενημερώνουμε τη λίστα με τα προϊόντα του καλαθιού
            adapter.notifyDataSetChanged();  // Ενημέρωση του RecyclerView
            updateTotal();  // Ενημέρωση του συνολικού ποσού
            checkIfCartIsEmpty();  // Ελέγχουμε αν το καλάθι είναι άδειο
            Toast.makeText(CartActivity.this, "Προστέθηκε στο καλάθι!", Toast.LENGTH_SHORT).show();  // Εμφάνιση μηνύματος
        });
    }

    // Ενημέρωση του συνολικού ποσού
    private void updateTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalTextView.setText("Σύνολο: €" + String.format("%.2f", total));  // Ενημέρωση του συνολικού ποσού
    }

    // Ελέγχουμε αν το καλάθι είναι άδειο
    private void checkIfCartIsEmpty() {
        if (cartItems.isEmpty()) {
            recyclerView.setVisibility(View.GONE);  // Απόκρυψη του RecyclerView αν το καλάθι είναι άδειο
            totalTextView.setVisibility(View.GONE);  // Απόκρυψη του συνολικού ποσού όταν το καλάθι είναι άδειο
            Toast.makeText(this, "Το καλάθι είναι άδειο!", Toast.LENGTH_SHORT).show();  // Εμφάνιση μηνύματος
        } else {
            recyclerView.setVisibility(View.VISIBLE);  // Εμφάνιση του RecyclerView όταν το καλάθι έχει προϊόντα
            totalTextView.setVisibility(View.VISIBLE);  // Εμφάνιση του συνολικού ποσού όταν το καλάθι έχει προϊόντα
        }
    }

    // Δημιουργία του μενού
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);  // Φορτώνουμε το μενού από το XML
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            openCart();  // Άνοιγμα του καλαθιού
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCart() {
        Intent intent = new Intent(this, CartDetailsActivity.class);  // Άνοιγμα της δραστηριότητας του καλαθιού
        startActivity(intent);
    }

}
