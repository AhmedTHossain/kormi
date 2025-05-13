package com.apptechbd.nibay.auth.presentation.registration;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.core.utils.ImageUtils;
import com.apptechbd.nibay.databinding.FragmentNidUploadBinding;

import java.io.File;

public class NidUploadFragment extends Fragment {
    private FragmentNidUploadBinding binding;
    private Uri selectedImageUri;
    private File imageFile;
    private boolean isImagePicked;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;

    public NidUploadFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;

                    imageFile = new ImageUtils().rotateImage(uri, requireContext());
                    binding.shapeableImageview.setImageURI(uri);
                    isImagePicked = true;
                    updateButtonState();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNidUploadBinding.inflate(inflater, container, false);
        initViewModel();

        binding.buttonSelectNid.setOnClickListener(v -> openImagePicker());
        binding.buttonNext.setOnClickListener(v -> uploadImage());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void uploadImage() {
        RegisterUserModel user = viewModel.getUser();
        Log.d("NIDUploadFragment", "User model inside NID Upload screen = " + user.toString());

        int currentFragment = viewPager2.getCurrentItem();
        viewModel.goToNextPage(currentFragment);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    private void openImagePicker() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
    }

    private void updateButtonState() {
        if (isImagePicked) {
            binding.buttonNext.setBackgroundColor(requireContext().getColor(R.color.md_theme_secondary));
            binding.buttonNext.setTextColor(requireContext().getColor(R.color.md_theme_background));
            binding.buttonNext.setEnabled(true);
        }
    }
}