package com.apptechbd.nibay.home.domain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.home.domain.models.EmployerRating;
import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerRatingAdapter extends RecyclerView.Adapter<EmployerRatingAdapter.ViewHolder> {
    private ArrayList<EmployerRating> employerRatings;
    private Context context;

    public EmployerRatingAdapter(ArrayList<EmployerRating> employerRatings, Context context) {
        this.employerRatings = employerRatings;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployerRatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review_from_employer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerRatingAdapter.ViewHolder holder, int position) {
        EmployerRating employerRating = employerRatings.get(position);

        if (employerRating.getImageUrl() != null)
            Glide.with(context).load(employerRating.getImageUrl()).into(holder.getCircleImageView());

        holder.getTextEmployerName().setText(employerRating.getEmployerName());
        holder.getTextComment().setText(employerRating.getComment());
        holder.getRatingBar().setRating(employerRating.getRating());
    }

    @Override
    public int getItemCount() {
        return employerRatings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private MaterialTextView textEmployerName, textComment;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_logo_employer);
            textEmployerName = itemView.findViewById(R.id.text_employer_title);
            textComment = itemView.findViewById(R.id.text_comment_employer);
            ratingBar = itemView.findViewById(R.id.rating_bar_employer);
        }

        public CircleImageView getCircleImageView() {
            return circleImageView;
        }

        public MaterialTextView getTextEmployerName() {
            return textEmployerName;
        }

        public MaterialTextView getTextComment() {
            return textComment;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }
    }
}
