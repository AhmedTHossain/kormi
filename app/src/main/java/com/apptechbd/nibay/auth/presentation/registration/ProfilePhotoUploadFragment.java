package com.apptechbd.nibay.auth.presentation.registration;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.ImageUtils;
import com.apptechbd.nibay.databinding.FragmentProfilePhotoUploadBinding;

import java.io.File;

public class ProfilePhotoUploadFragment extends Fragment {
    private FragmentProfilePhotoUploadBinding binding;
    private Uri selectedImageUri;
    private File imageFile;
    private boolean isImagePicked;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;

                    imageFile = new ImageUtils().rotateImage(uri, requireContext());
                    binding.circleImageView.setImageURI(uri);
                    isImagePicked = true;
                    updateButtonState();
                }
            });

    public ProfilePhotoUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilePhotoUploadBinding.inflate(inflater, container, false);

        binding.buttonSelectPhoto.setOnClickListener(v -> openImagePicker());

        return binding.getRoot();
    }

    private void openImagePicker() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
    }

    private void updateButtonState() {
        if (isImagePicked) {
            binding.buttonUploadNid.setBackgroundColor(requireContext().getColor(R.color.md_theme_secondary));
            binding.buttonUploadNid.setTextColor(requireContext().getColor(R.color.md_theme_background));
            binding.buttonUploadNid.setEnabled(true);
        }
    }
}