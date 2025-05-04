package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.FragmentNameInputBinding;

public class NameInputFragment extends Fragment {
    private FragmentNameInputBinding binding;
    private RegistrationViewModel viewModel;

    public NameInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNameInputBinding.inflate(inflater, container, false);

        initViewModel();
        restrictInputToOnlyAlphabets();

        binding.buttonNext.setOnClickListener(v -> {
            
        });

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    private void restrictInputToOnlyAlphabets(){
        binding.nameInputText.setFilters(new InputFilter[] {
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