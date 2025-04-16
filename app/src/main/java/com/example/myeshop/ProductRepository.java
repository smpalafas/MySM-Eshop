package com.example.myeshop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private DBHelper dbHelper;

    public ProductRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM products", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow("quantity"));
                int subcatId = cursor.getInt(cursor.getColumnIndexOrThrow("subcategory_id"));

                productList.add(new Product(id, title, description, price, quantity, subcatId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }
}
