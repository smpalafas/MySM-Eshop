package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private static final String TAG = "ShopActivity";
    private RecyclerView recyclerView;
    private ShopProductAdapter adapter;
    private List<Product> productList;
    private ProductRepository productRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        try {
            // Ρύθμιση του Toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Κατάστημα");
                }
            }

            recyclerView = findViewById(R.id.recyclerView);
            if (recyclerView != null) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                productRepo = new ProductRepository(this);
                productList = productRepo.getAllProducts();

                if (productList != null && !productList.isEmpty()) {
                    Log.d(TAG, "Φορτώθηκαν " + productList.size() + " προϊόντα");
                    adapter = new ShopProductAdapter(productList, this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Η λίστα προϊόντων είναι κενή ή null");
                }
            } else {
                Log.e(TAG, "Δεν βρέθηκε το recyclerView στο layout");
            }
        } catch (Exception e) {
            Log.e(TAG, "Σφάλμα κατά την αρχικοποίηση: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Δημιουργία του μενού
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_cart, menu);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Σφάλμα κατά τη δημιουργία του μενού: " + e.getMessage());
            return false;
        }
    }

    // Χειρισμός των επιλογών του μενού
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == R.id.action_cart) {
                // Άνοιγμα της οθόνης του καλαθιού
                Intent intent = new Intent(this, CartDetailsActivity.class);
                startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Σφάλμα κατά την επιλογή στοιχείου μενού: " + e.getMessage());
        }
        return super.onOptionsItemSelected(item);
    }
}