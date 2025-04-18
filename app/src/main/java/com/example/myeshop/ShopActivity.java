package com.example.myeshop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShopProductAdapter adapter;
    private List<Product> productList;
    private ProductRepository productRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productRepo = new ProductRepository(this);
        productList = productRepo.getAllProducts();

        adapter = new ShopProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);
    }
}
