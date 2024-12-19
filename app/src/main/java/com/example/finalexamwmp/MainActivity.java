package com.example.finalexamwmp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView3;
    private ImageView buttonLogout;
    private Button btnEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        textView3 = findViewById(R.id.textView3);
        buttonLogout = findViewById(R.id.buttonLogout);
        btnEnroll = findViewById(R.id.btnEnroll);

        // Get user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "User");

        // Set welcome message
        textView3.setText("Welcome, " + userName + "!");

        // Logout button action
        buttonLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Enroll button action
        btnEnroll.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EnrollmentActivity.class);
            startActivity(intent);
        });

    }
}
