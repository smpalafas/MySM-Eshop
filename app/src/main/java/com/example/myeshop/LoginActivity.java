package com.example.myeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    // Προκαθορισμένοι λογαριασμοί
    private HashMap<String, String> customers = new HashMap<>();
    private HashMap<String, String> admins = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Αρχικοποίηση λογαριασμών
        customers.put("customer1", "pass123");
        customers.put("customer2", "pass456");

        admins.put("admin1", "admin123");
        admins.put("admin2", "admin456");

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> checkLogin());
    }

    private void checkLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (customers.containsKey(username) && customers.get(username).equals(password)) {
            Toast.makeText(this, "Είσοδος ως Πελάτης", Toast.LENGTH_SHORT).show();
            // ξεκινάει δραστηριότητα για πελάτη
            startActivity(new Intent(this, CustomerActivity.class));
        } else if (admins.containsKey(username) && admins.get(username).equals(password)) {
            Toast.makeText(this, "Είσοδος ως Διαχειριστής", Toast.LENGTH_SHORT).show();
            // ξεκινάει δραστηριότητα για admin
            startActivity(new Intent(this, AdminActivity.class));
        } else {
            Toast.makeText(this, "Λάθος στοιχεία", Toast.LENGTH_SHORT).show();
        }
    }
}
