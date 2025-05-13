package com.example.myeshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerLink;

    private HashMap<String, String> customers = new HashMap<>();
    private HashMap<String, String> admins = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Φόρτωση αρχικών δεδομένων
        DataLoader loader = new DataLoader(getApplicationContext());
        loader.loadDataIfNeeded();

        // Προκαθορισμένοι χρήστες
        customers.put("customer1", "pass123");
        customers.put("customer2", "pass456");

        admins.put("admin1", "admin123");
        admins.put("admin2", "admin456");

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        loginButton.setOnClickListener(v -> checkLogin());

        registerLink.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void checkLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Προκαθορισμένοι πελάτες
        if (customers.containsKey(username) && customers.get(username).equals(password)) {
            Toast.makeText(this, "Είσοδος ως Πελάτης", Toast.LENGTH_SHORT).show();
            goToCustomer();
            return;
        }

        // Προκαθορισμένοι διαχειριστές
        if (admins.containsKey(username) && admins.get(username).equals(password)) {
            Toast.makeText(this, "Είσοδος ως Διαχειριστής", Toast.LENGTH_SHORT).show();
            goToAdmin();
            return;
        }

        // Έλεγχος βάσης δεδομένων
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT role FROM users WHERE username = ? AND password = ?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            String role = cursor.getString(0);
            cursor.close();

            if ("admin".equalsIgnoreCase(role)) {
                Toast.makeText(this, "Είσοδος ως Διαχειριστής (DB)", Toast.LENGTH_SHORT).show();
                goToAdmin();
            } else {
                Toast.makeText(this, "Είσοδος ως Πελάτης (DB)", Toast.LENGTH_SHORT).show();
                goToCustomer();
            }
        } else {
            cursor.close();
            Toast.makeText(this, "Λάθος στοιχεία σύνδεσης", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToCustomer() {
        Intent intent = new Intent(this, CustomerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void goToAdmin() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
