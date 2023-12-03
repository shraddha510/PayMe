package com.example.payme2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Perform login logic
                isValidLogin(username, password);
            }
        });

        // For user registration (example)
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Perform user registration logic
                registerUser(username, password);
            }
        });
    }

    private void navigateToGroupsActivity() {
        Intent intent = new Intent(MainActivity.this, GroupsActivity.class);
        startActivity(intent);
        finish();
    }

    private void isValidLogin(String username, String password) {
        // Your login validation logic
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String storedUsername = preferences.getString("username", "");
        String storedPassword = preferences.getString("password", "");

        boolean isValid = username.equals(storedUsername) && password.equals(storedPassword);

        if (isValid) {
            navigateToGroupsActivity();
        } else {
            Toast.makeText(MainActivity.this, "Invalid Credentials. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(String username, String password) {
        // Your user registration logic (e.g., store user data)
        // For simplicity, you might use SharedPreferences for user data storage
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(MainActivity.this, "User Registered!", Toast.LENGTH_SHORT).show();
        navigateToGroupsActivity();
    }
}
