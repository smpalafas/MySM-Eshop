package com.example.myeshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DataLoader {

    private final Context context;
    private final DBHelper dbHelper;

    public DataLoader(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    public void loadDataIfNeeded() {
        SharedPreferences prefs = context.getSharedPreferences("setup", Context.MODE_PRIVATE);
        boolean dataLoaded = prefs.getBoolean("data_loaded", false);

        if (!dataLoaded) {
            loadCategories();
            loadProducts();

            prefs.edit().putBoolean("data_loaded", true).apply();
            Log.d("DataLoader", "Data loaded into database.");
        }
    }

    private void loadCategories() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("categories_subcategories.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\(");
                String category = parts[0].trim();
                String subcatsRaw = parts[1].replace(")", "");
                long categoryId = DBUtils.insertCategory(db, category);

                String[] subcategories = subcatsRaw.split("@");
                for (String sub : subcategories) {
                    DBUtils.insertSubcategory(db, sub.trim(), (int) categoryId);
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.e("DataLoader", "Error loading categories", e);
        }
    }

    private void loadProducts() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("products.txt")));
            String line;
            String title = "", description = "", category = "", subcategory = "", priceStr = "", quantity = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Τίτλος:")) {
                    title = line.replace("Τίτλος:", "").trim();
                } else if (line.startsWith("Περιγραφή:")) {
                    description = line.replace("Περιγραφή:", "").trim();
                } else if (line.startsWith("Κατηγορία:")) {
                    category = line.replace("Κατηγορία:", "").trim();
                } else if (line.startsWith("Υποκατηγορία:")) {
                    subcategory = line.replace("Υποκατηγορία:", "").trim();
                } else if (line.startsWith("Τιμή:")) {
                    priceStr = line.replace("Τιμή:", "").trim().replace("€", "").replace(",", ".");
                } else if (line.startsWith("Ποσότητα:")) {
                    quantity = line.replace("Ποσότητα:", "").trim();
                    double price = Double.parseDouble(priceStr);
                    int subcategoryId = DBUtils.getSubcategoryId(db, subcategory);
                    if (subcategoryId != -1) {
                        DBUtils.insertProduct(db, title, description, price, quantity, subcategoryId);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.e("DataLoader", "Error loading products", e);
        }
    }
}
