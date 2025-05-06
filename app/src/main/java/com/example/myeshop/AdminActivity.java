package com.example.myeshop;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminActivity extends AppCompatActivity implements ProductAdapter.OnAddToCartClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private ProductRepository productRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productRepo = new ProductRepository(getApplicationContext());
        productList = productRepo.getAllProducts();

        adapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAddToCart(Product product, int quantity) {
        Toast.makeText(this, quantity + "x " + product.getTitle() + " προστέθηκαν στο καλάθι!", Toast.LENGTH_SHORT).show();
        // Εδώ μπορείς να το αποθηκεύσεις προσωρινά, να ενημερώσεις βάση, ή να το στείλεις σε καλάθι χρήστη
    }
}
