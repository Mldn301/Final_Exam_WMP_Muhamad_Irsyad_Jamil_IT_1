package com.example.finalexamwmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexamwmp.R;
import com.example.finalexamwmp.model.SummaryItem;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    // List to store the summary items that will be displayed
    private final List<SummaryItem> summaryList;

    // Constructor for the adapter, takes a list of SummaryItem objects
    public SummaryAdapter(List<SummaryItem> summaryList) {
        this.summaryList = summaryList;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);

        // Return a new ViewHolder instance with the inflated view
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        // Get the current item from the summary list based on position
        SummaryItem item = summaryList.get(position);

        // Set the subject and credit text in the ViewHolder
        holder.textViewSubject.setText(item.getSubject());
        holder.textViewCredit.setText(item.getCredit() + " credits");
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the summary list
        return summaryList.size();
    }

    // ViewHolder class to hold references to the views for each item
    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSubject; // TextView for the subject
        TextView textViewCredit;  // TextView for the credit value

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views for subject and credit
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
            textViewCredit = itemView.findViewById(R.id.textViewCredit);
        }
    }
}
