package com.apptechbd.nibay.home.domain.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.home.domain.model.ProfileDocument;
import com.apptechbd.nibay.home.presentation.HomeViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ProfileDocumentsAdapter extends RecyclerView.Adapter<ProfileDocumentsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProfileDocument> profileDocuments;
    private HomeViewModel homeViewModel;

    public ProfileDocumentsAdapter(Context context, ArrayList<ProfileDocument> profileDocuments, HomeViewModel homeViewModel) {
        this.context = context;
        this.profileDocuments = profileDocuments;
        this.homeViewModel = homeViewModel;
    }

    @NonNull
    @Override
    public ProfileDocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uploaded_document, parent, false);
        return new ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ProfileDocumentsAdapter.ViewHolder holder, int position) {
//        ProfileDocument profileDocument = profileDocuments.get(position);
//        holder.getDocumentTitle().setText(profileDocument.getDocumentTitle());
//
//        String fullImageUrl = "https://nibay.co" + profileDocument.getDocumentImage();
//        Glide.with(context).load(fullImageUrl).into(holder.getDocumentImage());
//
//        Log.d("ProfileDocumentsAdapter", "Full Image URL: " + fullImageUrl);
//
//        holder.itemView.setOnClickListener(v -> {
//            homeViewModel.onDocumentClicked(profileDocument.getDocumentTitle(), position);
//        });
//    }

    @Override
    public void onBindViewHolder(@NonNull ProfileDocumentsAdapter.ViewHolder holder, int position) {
        ProfileDocument profileDocument = profileDocuments.get(position);
        holder.getDocumentTitle().setText(profileDocument.getDocumentTitle());

        String imageUrl = profileDocument.getDocumentImage();

        // If image is local URI (e.g. content:// or file://), use it directly
        if (imageUrl.startsWith("content://") || imageUrl.startsWith("file://")) {
            Glide.with(context)
                    .load(Uri.parse(imageUrl))
                    .into(holder.getDocumentImage());
        } else {
            // Treat as remote URL
            String fullImageUrl = "https://nibay.co" + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .into(holder.getDocumentImage());
            Log.d("ProfileDocumentsAdapter", "Full Image URL: " + fullImageUrl);
        }

        holder.itemView.setOnClickListener(v -> {
            homeViewModel.onDocumentClicked(profileDocument.getDocumentTitle(), position);
        });
    }

    @Override
    public int getItemCount() {
        return profileDocuments.size();
    }

    public void updateDocument(String docType, String localUriString) {
        for (int i = 0; i < profileDocuments.size(); i++) {
            ProfileDocument doc = profileDocuments.get(i);
            if (doc.getDocumentTitle().equals(docType)) {
                doc.setDocumentImage(localUriString); // Update with local URI
                notifyItemChanged(i);
                Log.d("ProfileDocumentsAdapter", "Updated document: " + docType + " with URI: " + localUriString);
                return;
            }
        }

        // If not found, add a new document
        ProfileDocument newDoc = new ProfileDocument(docType, localUriString);
        profileDocuments.add(newDoc);
        notifyItemInserted(profileDocuments.size() - 1);
        Log.d("ProfileDocumentsAdapter", "Inserted new document: " + docType + " with URI: " + localUriString);
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
