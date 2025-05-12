package com.example.myeshop;

import android.content.ContentValues;
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
        try {
            DBHelper dbHelper = new DBHelper(context);
            db = dbHelper.getReadableDatabase();
        } catch (Exception e) {
            Log.e(TAG, "Σφάλμα κατά την αρχικοποίηση της βάσης δεδομένων: " + e.getMessage());
        }
    }

    public void updateProductQuantity(Product product) {
        SQLiteDatabase db = getWritableDatabase();  // Χρησιμοποιούμε το writable database για να κάνουμε την ενημέρωση
        ContentValues values = new ContentValues();
        values.put("quantity", product.getQuantity());  // Ενημερώνουμε την ποσότητα του προϊόντος

        // Ενημέρωση της βάσης δεδομένων για το συγκεκριμένο προϊόν
        db.update("products", values, "id = ?", new String[]{String.valueOf(product.getId())});
    }


    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        try {
            if (db == null) {
                Log.e(TAG, "Η βάση δεδομένων είναι null");
                return productList;
            }

            Cursor cursor = db.rawQuery("SELECT * FROM products", null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int idIndex = cursor.getColumnIndex("id");
                        int titleIndex = cursor.getColumnIndex("title");
                        int descriptionIndex = cursor.getColumnIndex("description");
                        int priceIndex = cursor.getColumnIndex("price");
                        int quantityIndex = cursor.getColumnIndex("quantity");  // Θα το κάνουμε int
                        int subcategoryIdIndex = cursor.getColumnIndex("subcategory_id");

                        // Έλεγχος ότι οι δείκτες είναι έγκυροι
                        int id = (idIndex != -1) ? cursor.getInt(idIndex) : 0;
                        String title = (titleIndex != -1) ? cursor.getString(titleIndex) : "Άγνωστο προϊόν";
                        String description = (descriptionIndex != -1) ? cursor.getString(descriptionIndex) : "";
                        double price = (priceIndex != -1) ? cursor.getDouble(priceIndex) : 0.0;

                        // Αντιμετώπιση της ποσότητας (convert την quantity από String σε int)
                        int quantity = (quantityIndex != -1) ? cursor.getInt(quantityIndex) : 0;  // Χρησιμοποιούμε getInt για την ποσότητα
                        int subcategoryId = (subcategoryIdIndex != -1) ? cursor.getInt(subcategoryIdIndex) : 0;

                        Product product = new Product(id, title, description, price, quantity, subcategoryId);
                        productList.add(product);
                        Log.d(TAG, "Φορτώθηκε προϊόν: " + title);

                    } while (cursor.moveToNext());
                } else {
                    Log.d(TAG, "Δεν βρέθηκαν προϊόντα στη βάση δεδομένων");
                }
                cursor.close();
            } else {
                Log.e(TAG, "Επιστράφηκε null cursor");
            }
        } catch (Exception e) {
            Log.e(TAG, "Σφάλμα κατά την ανάκτηση προϊόντων: " + e.getMessage());
        }

        return productList;
    }

}