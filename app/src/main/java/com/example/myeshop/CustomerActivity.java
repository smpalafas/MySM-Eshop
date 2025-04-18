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
            Intent intent = new Intent(CustomerActivity.this, ShopActivity.class);
            startActivity(intent);
        });
    }
}
