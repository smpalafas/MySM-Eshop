package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

        // Ρύθμιση του Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Κατάστημα");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productRepo = new ProductRepository(this);
        productList = productRepo.getAllProducts();

        adapter = new ShopProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);
    }

    // Δημιουργία του μενού
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    // Χειρισμός των επιλογών του μενού
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            // Άνοιγμα της οθόνης του καλαθιού
            Intent intent = new Intent(this, CartDetailsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}