package com.example.myeshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "eshop.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Δημιουργία πίνακα categories
        db.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL)");

        // Δημιουργία πίνακα subcategories
        db.execSQL("CREATE TABLE IF NOT EXISTS subcategories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "category_id INTEGER, " +
                "FOREIGN KEY (category_id) REFERENCES categories(id))");

        // Δημιουργία πίνακα products με όλα τα απαραίτητα πεδία
        db.execSQL("CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT, " +
                "price REAL NOT NULL, " +
                "quantity TEXT, " +
                "subcategory_id INTEGER, " +
                "FOREIGN KEY (subcategory_id) REFERENCES subcategories(id))");

        // Προσθήκη δοκιμαστικών προϊόντων για άμεση χρήση
        db.execSQL("INSERT INTO products (title, description, price, quantity) " +
                "VALUES ('Laptop', 'Powerful laptop for all your needs', 799.99, 'Διαθέσιμο')");
        db.execSQL("INSERT INTO products (title, description, price, quantity) " +
                "VALUES ('Smartphone', 'Latest smartphone with great features', 499.99, 'Διαθέσιμο')");
        db.execSQL("INSERT INTO products (title, description, price, quantity) " +
                "VALUES ('Headphones', 'High quality sound experience', 149.99, 'Διαθέσιμο')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS subcategories");
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }
}