package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalTextView;
    private List<CartItem> cartItems;
    private Button paymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);  // Φορτώνουμε το layout του καλαθιού

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Το καλάθι μου");
        }

        recyclerView = findViewById(R.id.cartRecyclerView);
        totalTextView = findViewById(R.id.totalAmountTextView);
        paymentButton = findViewById(R.id.paymentButton);  // Ανάθεση του κουμπιού Πληρωμής

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Φορτώνουμε τα προϊόντα στο καλάθι από τον CartManager
        cartItems = CartManager.getInstance().getCartItems();

        // Δημιουργούμε το Runnable για την ενημέρωση του συνολικού ποσού
        Runnable onUpdate = new Runnable() {
            @Override
            public void run() {
                updateTotal();  // Ενημέρωση του συνολικού ποσού
            }
        };

        // Δημιουργία του adapter και προσθήκη του Runnable για ανανέωση
        adapter = new CartAdapter(cartItems, onUpdate);
        recyclerView.setAdapter(adapter);

        updateTotal();  // Ενημέρωση του συνολικού ποσού αρχικά
        checkIfCartIsEmpty();  // Ελέγχουμε αν το καλάθι είναι άδειο

        // Όταν πατηθεί το κουμπί "Πληρωμή", ανοίγει η PaymentActivity
        paymentButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            startActivity(intent);  // Άνοιγμα της PaymentActivity
        });
    }

    // Ενημέρωση του συνολικού ποσού
    private void updateTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();  // Υπολογισμός του συνολικού ποσού
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);  // Φορτώνουμε το μενού από το XML
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // Πίσω στην προηγούμενη οθόνη όταν πατάς το back button
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            cartItems = CartManager.getInstance().getCartItems();  // Επαναφόρτωση των στοιχείων του καλαθιού
            adapter.notifyDataSetChanged();  // Ενημέρωση του adapter
            updateTotal();  // Ενημέρωση του συνολικού ποσού
            checkIfCartIsEmpty();  // Ελέγχουμε αν το καλάθι είναι άδειο
        }
    }

    // Στο σημείο που ολοκληρώνεται η παραγγελία και θέλουμε να αφαιρέσουμε τα προϊόντα
    public void completeOrder() {
        ProductRepository productRepo = new ProductRepository(this);

        for (CartItem item : cartItems) {
            int quantityPurchased = item.getQuantity();
            Product product = item.getProduct();

            // Ενημέρωση της ποσότητας για το κάθε προϊόν
            productRepo.updateProductQuantity(product, quantityPurchased);
        }

        Toast.makeText(this, "Η παραγγελία σας καταχωρήθηκε επιτυχώς!", Toast.LENGTH_SHORT).show();
        finish();  // Κλείνουμε την activity ή κάνουμε οποιαδήποτε άλλη ενέργεια
    }



}



