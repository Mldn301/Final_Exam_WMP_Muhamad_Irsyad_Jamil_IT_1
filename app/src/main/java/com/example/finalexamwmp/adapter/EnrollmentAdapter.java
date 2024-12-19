package com.example.finalexamwmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexamwmp.R;
import com.example.finalexamwmp.model.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentAdapter.EnrollmentViewHolder> {

    // List to store the enrollment data passed to the adapter
    private final List<Enrollment> enrollmentList;

    // List to track the selected enrollments (subjects with checkboxes checked)
    private final List<Enrollment> selectedEnrollments = new ArrayList<>();

    // Listener to notify the parent activity when the selection changes
    private final OnSelectionChangeListener selectionChangeListener;

    // Constructor to initialize the enrollment list and listener
    public EnrollmentAdapter(List<Enrollment> enrollmentList, OnSelectionChangeListener listener) {
        this.enrollmentList = enrollmentList;
        this.selectionChangeListener = listener;
    }

    // Creates a new ViewHolder when a new item needs to be created
    @NonNull
    @Override
    public EnrollmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout (item_enrollment.xml) for each list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enrollment, parent, false);
        return new EnrollmentViewHolder(view);
    }

    // Binds the data (subject and credit) to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull EnrollmentViewHolder holder, int position) {
        // Get the enrollment item at the current position
        Enrollment enrollment = enrollmentList.get(position);

        // Set the subject and credit text for this item
        holder.textViewSubject.setText(enrollment.getSubject());
        holder.textViewCredit.setText("Credits: " + enrollment.getCredit());

        // Set initial checkbox state
        holder.checkBox.setChecked(selectedEnrollments.contains(enrollment));

        // Handle checkbox change: update the selectedEnrollments list and notify the listener
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // If the checkbox is checked, add this enrollment to the selected list
                selectedEnrollments.add(enrollment);
            } else {
                // If unchecked, remove the enrollment from the selected list
                selectedEnrollments.remove(enrollment);
            }
            // Notify the listener of the change in the selected enrollments
            selectionChangeListener.onSelectionChange(selectedEnrollments);
        });
    }

    // Return the total number of items in the enrollment list
    @Override
    public int getItemCount() {
        return enrollmentList.size();
    }

    // Method to get the list of selected enrollments
    public List<Enrollment> getSelectedEnrollments() {
        return selectedEnrollments;
    }

    // ViewHolder class that holds the views for each item in the RecyclerView
    public static class EnrollmentViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSubject, textViewCredit;
        CheckBox checkBox;

        // Constructor to initialize the views for each item
        public EnrollmentViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find the TextViews and CheckBox within the item layout
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
            textViewCredit = itemView.findViewById(R.id.textViewCredit);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    // Interface to notify the parent activity when the selection changes
    public interface OnSelectionChangeListener {
        void onSelectionChange(List<Enrollment> selectedEnrollments);
    }
}
