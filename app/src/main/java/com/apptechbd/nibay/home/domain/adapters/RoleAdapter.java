package com.apptechbd.nibay.home.domain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {
    private ArrayList<String> roles;
    private Context context;

    public RoleAdapter(ArrayList<String> roles, Context context) {
        this.roles = roles;
        this.context = context;
    }

    @NonNull
    @Override
    public RoleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_role_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoleAdapter.ViewHolder holder, int position) {
        holder.getTextRole().setText(roles.get(position));
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialButton buttonRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonRole = itemView.findViewById(R.id.button_role);
        }

        public MaterialButton getTextRole() {
            return buttonRole;
        }
    }
}
