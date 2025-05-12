package com.example.myeshop;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DeliveryAddressActivity extends AppCompatActivity {

    private EditText addressEditText, phoneEditText;
    private TimePicker deliveryTimePicker;
    private Button confirmDeliveryButton;
    private ProductRepository productRepo;


    private void reduceProductQuantityInDatabase() {
        CartManager cartManager = CartManager.getInstance();
        List<CartItem> cartItems = cartManager.getCartItems();

        // Ενημέρωση της βάσης δεδομένων για κάθε προϊόν στο καλάθι
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int purchasedQuantity = cartItem.getQuantity();

            // Μείωση της ποσότητας του προϊόντος στη βάση δεδομένων
            product.setQuantity(product.getQuantity() - purchasedQuantity); // Ενημέρωση της διαθέσιμης ποσότητας

            // Ενημέρωση της βάσης δεδομένων
            productRepo.updateProductQuantity(product);  // Εδώ καλέστε την updateProductQuantity με το productRepo
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        // Αρχικοποιούμε το productRepo
        productRepo = new ProductRepository(this);

        // Εύρεση των στοιχείων του layout
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        deliveryTimePicker = findViewById(R.id.deliveryTimePicker);
        confirmDeliveryButton = findViewById(R.id.confirmDeliveryButton);

        confirmDeliveryButton.setOnClickListener(v -> {
            String address = addressEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            int hour = deliveryTimePicker.getHour();
            int minute = deliveryTimePicker.getMinute();

            // Ελέγχουμε αν η ώρα παράδοσης είναι έγκυρη
            if (hour < 8 || hour > 20) {
                Toast.makeText(DeliveryAddressActivity.this, "Η ώρα παράδοσης πρέπει να είναι μεταξύ 08:00 και 20:00", Toast.LENGTH_SHORT).show();
                return;
            }

            // Εάν όλα είναι σωστά
            Toast.makeText(DeliveryAddressActivity.this, "Η παραγγελία σας καταχωρήθηκε επιτυχώς!", Toast.LENGTH_SHORT).show();

            // Μείωση της ποσότητας των προϊόντων στη βάση δεδομένων
            reduceProductQuantityInDatabase();  // Καλέστε τη μέθοδο για να ενημερώσετε τη βάση

            // Κλείσιμο της Activity μετά από 5 δευτερόλεπτα
            new Handler().postDelayed(() -> {
                finish();  // Κλείνει την activity
            }, 5000);
        });
    }

}

