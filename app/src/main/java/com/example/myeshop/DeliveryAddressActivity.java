package com.example.myeshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeliveryAddressActivity extends AppCompatActivity {

    private EditText addressEditText, phoneEditText;
    private TimePicker deliveryTimePicker;
    private Button confirmDeliveryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        // Ανάθεση των στοιχείων του layout
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

            // Ελέγχουμε αν τα πεδία είναι κενά
            if (address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                return;
            }

            // Εδώ παίρνουμε το καλάθι
            ProductRepository productRepo = new ProductRepository(DeliveryAddressActivity.this);

            // Προσθέστε εδώ την λογική για να αφαιρέσετε τα προϊόντα
            for (CartItem item : CartManager.getInstance().getCartItems()) {
                // Καλέστε την updateProductQuantity για κάθε προϊόν
                int quantityPurchased = item.getQuantity();
                Product product = item.getProduct();

                // Ενημέρωση της ποσότητας για το κάθε προϊόν
                productRepo.updateProductQuantity(product, quantityPurchased);
            }

            // Μήνυμα επιτυχίας και κλείσιμο της Activity μετά από 5 δευτερόλεπτα
            Toast.makeText(DeliveryAddressActivity.this, "Η παραγγελία σας καταχωρήθηκε επιτυχώς!", Toast.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(this::finish, 5000);  // Κλείνει το activity μετά από 5 δευτερόλεπτα
        });
    }
}
