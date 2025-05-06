package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private ProductRepository productRepo;
    private Button openShopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // Ρύθμιση RecyclerView για την εμφάνιση των προϊόντων
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Φόρτωση προϊόντων από SQLite
        productRepo = new ProductRepository(this);
        productList = productRepo.getAllProducts();

        adapter = new ProductAdapter(productList, new ProductAdapter.OnAddToCartClickListener() {
            @Override
            public void onAddToCart(Product product, int quantity) {
                // εδώ αποθηκεύεις το προϊόν στο καλάθι (μπορείς να το κάνεις σε singleton ή SharedPreferences ή SQLite)
            }
        });
        recyclerView.setAdapter(adapter);



        // Ρύθμιση κουμπιού για να ανοίγει το ShopActivity
        openShopButton = findViewById(R.id.openShopButton);
        openShopButton.setOnClickListener(v -> {
            // Απλά ανοίγουμε το ShopActivity χωρίς να κλείσουμε το CustomerActivity
            Intent intent = new Intent(CustomerActivity.this, ShopActivity.class);
            startActivity(intent);
            // Δεν καλούμε finish() για να μπορεί ο χρήστης να επιστρέψει στην CustomerActivity
        });
    }
}
