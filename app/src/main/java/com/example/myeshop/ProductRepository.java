package com.example.myeshop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private SQLiteDatabase db;

    public ProductRepository(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow("quantity"));
                int subcategoryId = cursor.getInt(cursor.getColumnIndexOrThrow("subcategory_id"));

                Product product = new Product(id, title, description, price, quantity, subcategoryId);
                productList.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return productList;
    }
}
