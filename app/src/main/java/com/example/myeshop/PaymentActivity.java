package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private RadioButton storePickupRadioButton, deliveryRadioButton;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Σύνδεση με τα στοιχεία του layout
        storePickupRadioButton = findViewById(R.id.storePickupRadioButton);
        deliveryRadioButton = findViewById(R.id.deliveryRadioButton);
        confirmButton = findViewById(R.id.confirmButton);

        // Επιβεβαίωση παραγγελίας
        confirmButton.setOnClickListener(v -> {
            if (storePickupRadioButton.isChecked()) {
                // ✅ Παραλαβή από το κατάστημα
                Toast.makeText(this, "Η παραγγελία σας καταχωρήθηκε επιτυχώς", Toast.LENGTH_LONG).show();

                // Αφαίρεση προϊόντων από τη βάση
                CartManager cartManager = CartManager.getInstance();

                List<CartItem> items = cartManager.getCartItems();
                ProductRepository productRepo = new ProductRepository(this);

                for (CartItem item : items) {
                    productRepo.updateProductQuantity(item.getProduct(), item.getQuantity());
                }

                // Άδειασμα καλαθιού
                cartManager.clearCart();

                // Κλείσιμο εφαρμογής μετά από 5 δευτερόλεπτα
                new Handler().postDelayed(() -> {
                    finishAffinity();  // Τερματισμός όλων των activities
                    System.exit(0);    // Τερματισμός της εφαρμογής
                }, 5000);

            } else if (deliveryRadioButton.isChecked()) {
                
                Toast.makeText(this, "Επιλέξατε Delivery", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PaymentActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("delivery_method", "delivery");
                startActivity(intent);

            } else {
                
                Toast.makeText(this, "Παρακαλώ επιλέξτε έναν τρόπο παράδοσης", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
