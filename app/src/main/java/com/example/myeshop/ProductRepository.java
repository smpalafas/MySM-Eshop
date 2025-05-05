package com.example.myeshop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private SQLiteDatabase db;
    private static final String TAG = "ProductRepository";

    public ProductRepository(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM products", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int titleIndex = cursor.getColumnIndex("title");
                    int descriptionIndex = cursor.getColumnIndex("description");
                    int priceIndex = cursor.getColumnIndex("price");
                    int quantityIndex = cursor.getColumnIndex("quantity");
                    int subcategoryIdIndex = cursor.getColumnIndex("subcategory_id");

                    int id = cursor.getInt(idIndex != -1 ? idIndex : 0);
                    String title = cursor.getString(titleIndex != -1 ? titleIndex : 1);

                    // Χειρισμός της περίπτωσης που λείπει το πεδίο description
                    String description = "";
                    if (descriptionIndex != -1) {
                        description = cursor.getString(descriptionIndex);
                    }

                    double price = cursor.getDouble(priceIndex != -1 ? priceIndex : 2);

                    // Χειρισμός της περίπτωσης που λείπει το πεδίο quantity
                    String quantity = "Διαθέσιμο";
                    if (quantityIndex != -1) {
                        quantity = cursor.getString(quantityIndex);
                    }

                    // Χειρισμός της περίπτωσης που λείπει το πεδίο subcategory_id
                    int subcategoryId = 0;
                    if (subcategoryIdIndex != -1) {
                        subcategoryId = cursor.getInt(subcategoryIdIndex);
                    }

                    Product product = new Product(id, title, description, price, quantity, subcategoryId);
                    productList.add(product);

                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting products: " + e.getMessage());
        }



        return productList;
    }
}