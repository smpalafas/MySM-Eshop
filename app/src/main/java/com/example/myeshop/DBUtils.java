package com.example.myeshop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtils {

    public static long insertCategory(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        return db.insert("categories", null, values);
    }

    public static long insertSubcategory(SQLiteDatabase db, String name, int categoryId) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category_id", categoryId);
        return db.insert("subcategories", null, values);
    }

    public static long insertProduct(SQLiteDatabase db, String title, String description, double price, String quantity, int subcategoryId) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("price", price);
        values.put("quantity", quantity);
        values.put("subcategory_id", subcategoryId);
        return db.insert("products", null, values);
    }

    public static int getSubcategoryId(SQLiteDatabase db, String subcategoryName) {
        Cursor cursor = db.rawQuery("SELECT id FROM subcategories WHERE name = ?", new String[]{subcategoryName});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        cursor.close();
        return -1;
    }
}
