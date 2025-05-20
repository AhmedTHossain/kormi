package com.apptechbd.nibay.home.domain.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.home.domain.model.ProfileDocument;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ProfileDocumentsAdapter extends RecyclerView.Adapter<ProfileDocumentsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProfileDocument> profileDocuments;

    public ProfileDocumentsAdapter(Context context, ArrayList<ProfileDocument> profileDocuments) {
        this.context = context;
        this.profileDocuments = profileDocuments;
    }

    @NonNull
    @Override
    public ProfileDocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uploaded_document, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileDocumentsAdapter.ViewHolder holder, int position) {
        ProfileDocument profileDocument = profileDocuments.get(position);
        holder.getDocumentTitle().setText(profileDocument.getDocumentTitle());

        String fullImageUrl = "https://nibay.co" + profileDocument.getDocumentImage();
        Glide.with(context).load(fullImageUrl).into(holder.getDocumentImage());

        Log.d("ProfileDocumentsAdapter", "Full Image URL: " + fullImageUrl);


    }

    @Override
    public int getItemCount() {
        return profileDocuments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView documentTitle;
        private ShapeableImageView documentImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            documentTitle = itemView.findViewById(R.id.text_document_name);
            documentImage = itemView.findViewById(R.id.image_document);
        }

        public MaterialTextView getDocumentTitle() {
            return documentTitle;
        }

        public void setDocumentTitle(MaterialTextView documentTitle) {
            this.documentTitle = documentTitle;
        }

        public ShapeableImageView getDocumentImage() {
            return documentImage;
        }

        public void setDocumentImage(ShapeableImageView documentImage) {
            this.documentImage = documentImage;
        }
    }
}
