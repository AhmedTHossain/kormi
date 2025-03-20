package com.apptechbd.nibay.home.domain.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class FollowedEmployerAdapter extends RecyclerView.Adapter<FollowedEmployerAdapter.ViewHolder> {
    private ArrayList<FollowedEmployer> followedEmployers;
    private Context context;

    public FollowedEmployerAdapter(ArrayList<FollowedEmployer> followedEmployers, Context context) {
        this.followedEmployers = followedEmployers;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowedEmployerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_followed_companies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowedEmployerAdapter.ViewHolder holder, int position) {
        FollowedEmployer followedEmployer = followedEmployers.get(position);
        holder.getTextCompanyName().setText(followedEmployer.getName());

        if (followedEmployer.getProfilePhoto() != null) {
            String completeUrl = "https://nibay.co/"+followedEmployer.getProfilePhoto();

            Log.d("FollowedEmployerAdapter","company logo: "+completeUrl);
            Glide.with(context).load(completeUrl).into(holder.getImgCompanyLogo());
        }
    }

    @Override
    public int getItemCount() {
        return followedEmployers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView textCompanyName;
        private ShapeableImageView imgCompanyLogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCompanyName = itemView.findViewById(R.id.text_company_name);
            imgCompanyLogo = itemView.findViewById(R.id.img_company_logo);
        }

        public MaterialTextView getTextCompanyName() {
            return textCompanyName;
        }

        public ShapeableImageView getImgCompanyLogo() {
            return imgCompanyLogo;
        }
    }
}
