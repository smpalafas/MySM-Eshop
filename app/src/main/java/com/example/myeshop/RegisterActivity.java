package com.example.myeshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText emailInput, usernameInput, passwordInput;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.emailInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                return;
            }

            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Έλεγχος για διπλό email ή username
            Cursor check = db.rawQuery("SELECT * FROM users WHERE email = ? OR username = ?", new String[]{email, username});
            if (check.moveToFirst()) {
                Toast.makeText(this, "Το email ή το username χρησιμοποιείται ήδη", Toast.LENGTH_LONG).show();
                check.close();
                return;
            }
            check.close();

            ContentValues values = new ContentValues();
            values.put("email", email);
            values.put("username", username);
            values.put("password", password);  // μπορείς να βάλεις hash
            values.put("role", "customer");

            long result = db.insert("users", null, values);
            if (result != -1) {
                Toast.makeText(this, "Η εγγραφή ολοκληρώθηκε!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Αποτυχία εγγραφής", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
