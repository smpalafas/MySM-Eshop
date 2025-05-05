package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerActivity extends AppCompatActivity {

    private Button openShopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        openShopButton = findViewById(R.id.openShopButton);

        openShopButton.setOnClickListener(v -> {
            // Απλά ανοίγουμε το ShopActivity χωρίς να κλείσουμε το CustomerActivity
            Intent intent = new Intent(CustomerActivity.this, ShopActivity.class);
            startActivity(intent);
            // ΔΕΝ καλούμε finish() εδώ για να μπορεί ο χρήστης να επιστρέψει
        });
    }

    // Δεν χρειάζεται να κάνουμε override το onBackPressed() επειδή θέλουμε την προεπιλεγμένη συμπεριφορά
}