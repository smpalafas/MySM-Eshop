package com.example.myeshop;

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
                Toast.makeText(this, "Επιλέξατε Παραλαβή από το κατάστημα", Toast.LENGTH_SHORT).show();
                // Εδώ μπορείς να κάνεις ό,τι θέλεις, π.χ. επιστροφή ή αποστολή δεδομένων στην επόμενη οθόνη
            } else if (deliveryRadioButton.isChecked()) {
                Toast.makeText(this, "Επιλέξατε Delivery", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Παρακαλώ επιλέξτε έναν τρόπο παράδοσης", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
