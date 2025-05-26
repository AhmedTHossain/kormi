package com.apptechbd.nibay.jobads.domain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.jobads.domain.model.Requirement;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RequirementsAdapter extends RecyclerView.Adapter<RequirementsAdapter.ViewHolder> {
    private ArrayList<Requirement> requirements;
    private Context context;

    public RequirementsAdapter(ArrayList<Requirement> requirements, Context context) {
        this.requirements = requirements;
        this.context = context;
    }

    @NonNull
    @Override
    public RequirementsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_requirement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequirementsAdapter.ViewHolder holder, int position) {
        Requirement requirement = requirements.get(position);
        holder.getTitle().setText(requirement.getTitle());
        holder.getDescription().setText(requirement.getDescription());
    }

    @Override
    public int getItemCount() {
        return requirements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            description = itemView.findViewById(R.id.text_title_requirements);
        }

        public MaterialTextView getTitle() {
            return title;
        }

        public MaterialTextView getDescription() {
            return description;
        }
    }
}
