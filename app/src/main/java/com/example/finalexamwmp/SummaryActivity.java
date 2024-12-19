package com.example.finalexamwmp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexamwmp.adapter.SummaryAdapter;
import com.example.finalexamwmp.model.SummaryItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SummaryAdapter adapter;
    private List<SummaryItem> summaryList;
    private DatabaseReference summaryRef;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize back button
        backButton = findViewById(R.id.backButton);

        // Initialize Firebase reference
        summaryRef = FirebaseDatabase.getInstance().getReference("summary");

        // Initialize list and adapter
        summaryList = new ArrayList<>();
        adapter = new SummaryAdapter(summaryList);
        recyclerView.setAdapter(adapter);

        // Fetch summary data
        fetchSummaryData();

        // Back button action to navigate back to EnrollmentActivity
        backButton.setOnClickListener(v -> navigateBack());
    }

    // Find the available data in firebase
    private void fetchSummaryData() {
        summaryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                summaryList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String subject = child.getKey();
                        String credit = child.getValue(String.class);

                        if (subject != null && credit != null) {
                            summaryList.add(new SummaryItem(subject, credit));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // if not available data in firebase
                    Toast.makeText(SummaryActivity.this, "No summary data available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SummaryActivity.this, "Failed to load summary: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // This is the function for navigate to back to EnrollmentActivity
    private void navigateBack() {
        Intent intent = new Intent(SummaryActivity.this, EnrollmentActivity.class);
        startActivity(intent);
        finish();
    }
}
