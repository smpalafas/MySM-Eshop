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
            db = dbHelper.getWritableDatabase();  // Χρησιμοποιούμε getWritableDatabase για να έχουμε δικαιώματα εγγραφής
        } catch (Exception e) {
            Log.e(TAG, "Σφάλμα κατά την αρχικοποίηση της βάσης δεδομένων: " + e.getMessage());
        }
    }

    // Μέθοδος για ενημέρωση της ποσότητας του προϊόντος
    public void updateProductQuantity(Product product, int quantityPurchased) {
        int newQuantity = product.getQuantity() - quantityPurchased;

        if (newQuantity >= 0) {
            // Δημιουργία ContentValues για την ενημέρωση της ποσότητας
            ContentValues values = new ContentValues();
            values.put("quantity", newQuantity);

            // Ενημέρωση του προϊόντος στη βάση δεδομένων
            db.update("products", values, "id = ?", new String[]{String.valueOf(product.getId())});
            Log.d(TAG, "Η ποσότητα του προϊόντος ενημερώθηκε: " + product.getTitle() + " Νέα ποσότητα: " + newQuantity);
        } else {
            Log.e(TAG, "Δεν υπάρχει επαρκής ποσότητα για το προϊόν: " + product.getTitle());
        }
    }

    // Μέθοδος για να πάρω όλα τα προϊόντα από τη βάση δεδομένων
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
                        int quantityIndex = cursor.getColumnIndex("quantity");
                        int subcategoryIdIndex = cursor.getColumnIndex("subcategory_id");

                        // Έλεγχος ότι οι δείκτες είναι έγκυροι
                        int id = cursor.getInt(idIndex);
                        String title = cursor.getString(titleIndex);
                        String description = cursor.getString(descriptionIndex);
                        double price = cursor.getDouble(priceIndex);
                        int quantity = cursor.getInt(quantityIndex);  // Πόσες μονάδες υπάρχουν
                        int subcategoryId = cursor.getInt(subcategoryIdIndex);

                        Product product = new Product(id, title, description, price, quantity, subcategoryId);
                        productList.add(product);
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
