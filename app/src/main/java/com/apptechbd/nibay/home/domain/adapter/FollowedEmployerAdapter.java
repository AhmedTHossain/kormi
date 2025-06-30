package com.apptechbd.nibay.home.domain.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.ViewStyler;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.presentation.HomeViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class FollowedEmployerAdapter extends RecyclerView.Adapter<FollowedEmployerAdapter.ViewHolder> {
    private ArrayList<FollowedEmployer> followedEmployers;
    private Context context;
    private HomeViewModel homeViewModel;

    public FollowedEmployerAdapter(ArrayList<FollowedEmployer> followedEmployers, Context context, HomeViewModel homeViewModel) {
        this.followedEmployers = followedEmployers;
        this.context = context;
        this.homeViewModel = homeViewModel;
    }

    @NonNull
    @Override
    public FollowedEmployerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_followed_companies, parent, false);
        return new ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        FollowedEmployer followedEmployer = followedEmployers.get(position);
//
//        // Load image
//        if (followedEmployer.getProfilePhoto() != null) {
//            String completeUrl = "https://nibay.co/" + followedEmployer.getProfilePhoto();
//            Glide.with(context)
//                    .load(completeUrl)
//                    .into(holder.imgCompanyLogo);
//        } else {
//            holder.imgCompanyLogo.setImageResource(R.drawable.employer_logo_placeholder_2);
//        }
//
//        // Add stroke if selected
//        if (followedEmployer.getSelected()) {
//            holder.imgCompanyLogo.setStrokeColor(
//                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.md_theme_primary)));
//            holder.imgCompanyLogo.setStrokeWidth(
//                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics()));
//        } else {
//            holder.imgCompanyLogo.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT));
//            holder.imgCompanyLogo.setStrokeWidth(0f);
//        }
//
//        // Handle click
//        holder.itemView.setOnClickListener(v -> {
//            Log.d("FollowedEmployerAdapter", "Selected company id: " + followedEmployer.getId());
//            homeViewModel.onFollowedCompanyClicked(followedEmployer.getId());
//        });
//    }

    @Override
    public void onBindViewHolder(@NonNull FollowedEmployerAdapter.ViewHolder holder, int position) {
        FollowedEmployer followedEmployer = followedEmployers.get(position);

        // Load image with Glide
        if (followedEmployer.getProfilePhoto() != null) {
            String completeUrl = "https://nibay.co/" + followedEmployer.getProfilePhoto();
            Glide.with(context).load(completeUrl).into(holder.imgCompanyLogo);
        } else {
            holder.imgCompanyLogo.setImageResource(R.drawable.employer_logo_placeholder_2);
        }

        // Set selection stroke
        if (followedEmployer.getSelected()) {
            holder.cardView.setStrokeColor(ContextCompat.getColor(context, R.color.md_theme_primary));
            holder.cardView.setStrokeWidth(4); // thickness in dp (Material handles unit)
        } else {
            holder.cardView.setStrokeColor(Color.TRANSPARENT);
            holder.cardView.setStrokeWidth(0);
        }

        // Handle click
        holder.cardView.setOnClickListener(v -> {
            homeViewModel.onFollowedCompanyClicked(followedEmployer.getId());
        });
    }


    @Override
    public int getItemCount() {
        return followedEmployers.size();
    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private final ShapeableImageView imgCompanyLogo;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgCompanyLogo = itemView.findViewById(R.id.img_company_logo);
//        }
//
//        public ShapeableImageView getImgCompanyLogo() {
//            return imgCompanyLogo;
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imgCompanyLogo;
        MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCompanyLogo = itemView.findViewById(R.id.img_company_logo);
            cardView = itemView.findViewById(R.id.card_company_logo);
        }
    }
}
