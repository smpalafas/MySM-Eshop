package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private RadioButton storePickupRadioButton, deliveryRadioButton;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Εύρεση των στοιχείων του layout
        storePickupRadioButton = findViewById(R.id.storePickupRadioButton);
        deliveryRadioButton = findViewById(R.id.deliveryRadioButton);
        confirmButton = findViewById(R.id.confirmButton);

        // Ρύθμιση του listener για το κουμπί Επιβεβαίωση
        confirmButton.setOnClickListener(v -> {
            if (storePickupRadioButton.isChecked()) {
                // Αν επιλέξεις παραλαβή από το κατάστημα
                Toast.makeText(this, "Επιλέξατε Παραλαβή από το κατάστημα", Toast.LENGTH_SHORT).show();
            } else if (deliveryRadioButton.isChecked()) {
                // Αν επιλέξεις Delivery
                Toast.makeText(this, "Επιλέξατε Delivery", Toast.LENGTH_SHORT).show();

                // Εδώ ανοίγουμε τη δραστηριότητα DeliveryAddressActivity
                Intent intent = new Intent(PaymentActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("delivery_method", "delivery");  // Περνάμε την επιλογή
                startActivity(intent);  // Άνοιγμα της DeliveryAddressActivity
            } else {
                // Αν δεν έχει επιλεγεί κάποια επιλογή
                Toast.makeText(this, "Παρακαλώ επιλέξτε έναν τρόπο παράδοσης", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
