package com.example.myeshop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class CustomerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productList.add(new Product("Milk", 1.20));
        productList.add(new Product("Bread", 0.80));
        productList.add(new Product("Cheese", 2.50));
        productList.add(new Product("Apples", 1.10));
        productList.add(new Product("Orange Juice", 1.90));

        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
    }
}
