package com.example.myeshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "eshop.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Δημιουργία πίνακα Κατηγοριών
        db.execSQL("CREATE TABLE categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL UNIQUE)");

        // Δημιουργία πίνακα Υποκατηγοριών
        db.execSQL("CREATE TABLE subcategories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "category_id INTEGER," +
                "FOREIGN KEY (category_id) REFERENCES categories(id))");

        // Δημιουργία πίνακα Προϊόντων
        db.execSQL("CREATE TABLE products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "description TEXT," +
                "price REAL," +
                "quantity TEXT," +
                "subcategory_id INTEGER," +
                "FOREIGN KEY (subcategory_id) REFERENCES subcategories(id))");

        Log.d("DBHelper", "Database created successfully!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Σε περίπτωση αναβάθμισης, drop και επαναδημιουργία
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS subcategories");
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }
}
