package com.apptechbd.nibay.auth.presentation.registration;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.ImageUtils;
import com.apptechbd.nibay.databinding.FragmentEducationTrascriptUploadBinding;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class EducationTrascriptUploadFragment extends Fragment {
    private FragmentEducationTrascriptUploadBinding binding;
    private RegistrationViewModel registrationViewModel;

    private Uri selectedImageUri;
    private File imageFile;
    private boolean isImagePicked;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;
    private Uri resultUri;
    private final ActivityResultLauncher<Intent> cropImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    final Intent data = result.getData();
                    resultUri = UCrop.getOutput(data);
                    if (resultUri != null) {
                        imageFile = new ImageUtils().rotateImage(resultUri, requireContext());

                        Log.d("ProfileFragment", "image file cropped = " + imageFile);

                        binding.shapeableImageview.setImageURI(resultUri);
                        isImagePicked = true;
                        updateButtonState();
                    }
                } else if (result.getResultCode() == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(result.getData());
                    Toast.makeText(requireContext(), "Crop failed: " + cropError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                Uri destinationUri = Uri.fromFile(new File(requireContext().getCacheDir(), "cropped_image.jpg"));

                if (uri != null) {
                    Intent uCropIntent = UCrop.of(uri, destinationUri)
                            .withAspectRatio(3, 2)
                            .getIntent(requireContext());

                    cropImageLauncher.launch(uCropIntent);
                }
            });

    public EducationTrascriptUploadFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEducationTrascriptUploadBinding.inflate(inflater, container, false);
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