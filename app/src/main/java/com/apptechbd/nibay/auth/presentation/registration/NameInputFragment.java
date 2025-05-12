package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentNameInputBinding;

public class NameInputFragment extends Fragment {
    private FragmentNameInputBinding binding;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;

    public NameInputFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNameInputBinding.inflate(inflater, container, false);

        initViewModel();
        restrictInputToOnlyAlphabets();

        binding.buttonNext.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.nameInputText.getText())) {

                RegisterUserModel user = viewModel.getUser();
                user.setFullName(binding.nameInputText.getText().toString());
                viewModel.setUser(user);

                int currentFragment = viewPager2.getCurrentItem();
                viewModel.goToNextPage(currentFragment);
            } else
                new HelperClass().showSnackBar(binding.getRoot(), getString(R.string.name_input_empty_disclaimer));
        });

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    private void restrictInputToOnlyAlphabets() {
        binding.nameInputText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source != null && !source.toString().matches("[a-zA-Z ]*")) { // Allow spaces along with alphabets
                            return "";  // Reject non-alphabetic characters
                        }
                        return null;  // Allow alphabetic characters and spaces
                    }
                }
        });
    }
}