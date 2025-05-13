package com.apptechbd.nibay.auth.presentation.registration;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.core.utils.NidNumberFormatter;
import com.apptechbd.nibay.core.utils.NidNumberValidator;
import com.apptechbd.nibay.databinding.FragmentNidInputBinding;

public class NidInputFragment extends Fragment {
    private FragmentNidInputBinding binding;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;

    public NidInputFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNidInputBinding.inflate(inflater, container, false);
        initViewModel();

        NidNumberFormatter.formatNidNumber(binding.nidInputText);
        binding.nidInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Remove error message as soon as user starts typing
                if (s.length() > 0)
                    binding.nidInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Drawable drawable;
                if (NidNumberValidator.isValidBangladeshiNidNumber(s.toString()))
                    drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_correct_input);
                else
                    drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_alert);
                binding.nidInputText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        });

        binding.buttonNext.setOnClickListener(v -> validateFields());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    public void initViewModel(){
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    private void validateFields() {
        boolean isValid = true;
        String nidNumber = binding.nidInputText.getText().toString();
        if (nidNumber.isEmpty()){
            binding.nidInputLayout.setError(getString(R.string.error_empty_nid_field));
            isValid = false;
        }
       else if (!NidNumberValidator.isValidBangladeshiNidNumber(nidNumber)){
           binding.nidInputLayout.setError(getString(R.string.error_invalid_nid_number));
           isValid = false;
       }
       if (isValid) {
           RegisterUserModel user = viewModel.getUser();
           user.setNidNumber(new HelperClass().formatPhoneNumber(binding.nidInputText.getText().toString().trim()));

           int currentFragment = viewPager2.getCurrentItem();
           viewModel.goToNextPage(currentFragment);
       }
    }
}