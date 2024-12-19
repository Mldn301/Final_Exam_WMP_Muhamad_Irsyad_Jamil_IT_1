package com.example.finalexamwmp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexamwmp.adapter.EnrollmentAdapter;
import com.example.finalexamwmp.model.Enrollment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private EnrollmentAdapter adapter;
    private List<Enrollment> enrollmentList;
    private Button finalizeButton, summaryButton;
    private TextView creditIndicator;
    private ImageView backButton;
    private int totalCredits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        // Initialize UI elements
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        finalizeButton = findViewById(R.id.finalizeButton);
        creditIndicator = findViewById(R.id.creditIndicator);
        summaryButton = findViewById(R.id.summaryButton);
        backButton = findViewById(R.id.backButton);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("enrollments");

        // Initialize list and adapter
        enrollmentList = new ArrayList<>();
        adapter = new EnrollmentAdapter(enrollmentList, this::updateCreditIndicator);
        recyclerView.setAdapter(adapter);

        // Fetch data from Firebase
        fetchEnrollments();

        // Finalize button action
        finalizeButton.setOnClickListener(v -> finalizeEnrollment());

        // Summary button action to navigate to SummaryActivity
        summaryButton.setOnClickListener(v -> goToSummaryActivity());

        // Back button action to navigate to MainActivity
        backButton.setOnClickListener(v -> navigateBack());
    }

    private void fetchEnrollments() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                enrollmentList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String subject = child.child("subject").getValue(String.class);
                    String creditStr = child.child("credit").getValue(String.class);

                    if (subject != null && creditStr != null) {
                        try {
                            enrollmentList.add(new Enrollment(subject, creditStr));
                        } catch (NumberFormatException e) {
                            Toast.makeText(EnrollmentActivity.this, "Invalid credit value!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EnrollmentActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCreditIndicator(List<Enrollment> selectedEnrollments) {
        totalCredits = 0;
        for (Enrollment enrollment : selectedEnrollments) {
            try {
                totalCredits += Integer.parseInt(enrollment.getCredit());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid credit value!", Toast.LENGTH_SHORT).show();
            }
        }
        creditIndicator.setText("Total Credits: " + totalCredits);
    }

    private void finalizeEnrollment() {
        if (totalCredits <= 24) {
            saveSummaryData();
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra("totalCredits", totalCredits);
            startActivity(intent);
            Toast.makeText(this, "Enrollment finalized successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Total credits exceed the limit of 24!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSummaryData() {
        DatabaseReference summaryRef = FirebaseDatabase.getInstance().getReference("summary");

        // Remove old data before saving new data
        summaryRef.removeValue();

        // Add the selected enrollments (subjects and credits) to the "summary" node
        for (Enrollment enrollment : adapter.getSelectedEnrollments()) {
            summaryRef.child(enrollment.getSubject()).setValue(enrollment.getCredit());
        }

        // Show a Toast message indicating the summary has been saved
        Toast.makeText(this, "Summary saved to database!", Toast.LENGTH_SHORT).show();
    }

    // This is the function to navigate toSummaryActivity
    private void goToSummaryActivity() {
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

    // This is the function to navigate to MainActivity
    private void navigateBack() {
        Intent intent = new Intent(EnrollmentActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
