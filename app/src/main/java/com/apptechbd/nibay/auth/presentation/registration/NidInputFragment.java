package com.apptechbd.nibay.auth.presentation.registration;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.presentation.login.OtpActivity;
import com.apptechbd.nibay.core.utils.NidNumberFormatter;
import com.apptechbd.nibay.core.utils.NidNumberValidator;
import com.apptechbd.nibay.databinding.FragmentNidInputBinding;

public class NidInputFragment extends Fragment {
    private FragmentNidInputBinding binding;
    public NidInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNidInputBinding.inflate(inflater, container, false);

        NidNumberFormatter.formatNidNumber(binding.nidInputText);
        binding.nidInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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

        binding.buttonSetNidNumber.setOnClickListener(v -> validateFields());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void validateFields() {
        boolean isValid = true;
        String nidNumber = binding.nidInputText.getText().toString();
        if (nidNumber.isEmpty()){
            binding.nidInputLayout.setError(getString(R.string.error_empty_nid_field));
            isValid = false;
        }
       else if (!NidNumberValidator.isValidBangladeshiNidNumber(nidNumber)){
           binding.nidInputLayout.setError(getString(R.string.error_invalid_phone_number));
           isValid = false;
       }
       if (isValid) {

       }
    }
}