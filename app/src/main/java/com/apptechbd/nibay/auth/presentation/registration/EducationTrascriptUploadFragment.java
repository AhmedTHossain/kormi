package com.apptechbd.nibay.auth.presentation.registration;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.ImageUtils;
import com.apptechbd.nibay.databinding.FragmentEducationTrascriptUploadBinding;

import java.io.File;

public class EducationTrascriptUploadFragment extends Fragment {
    private FragmentEducationTrascriptUploadBinding binding;
    private RegistrationViewModel registrationViewModel;

    private Uri selectedImageUri;
    private File imageFile;
    private boolean isImagePicked;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;
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

    public EducationTrascriptUploadFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEducationTrascriptUploadBinding.inflate(inflater,container,false);
        initViewModel();

        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        binding.buttonSelectNid.setOnClickListener(v -> openImagePicker());

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentFragment = viewPager2.getCurrentItem();
                viewModel.goToNextPage(currentFragment);
            }
        });

        return binding.getRoot();
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