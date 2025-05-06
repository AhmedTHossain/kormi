package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapter.RoleAdapter;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.databinding.FragmentExperienceInputBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ExperienceInputFragment extends Fragment {
    private FragmentExperienceInputBinding binding;
    private RoleAdapter adapter;
    private RegistrationViewModel viewModel;

    public ExperienceInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExperienceInputBinding.inflate(inflater, container, false);
        initViewModel();

//        String[] experienceRanges = requireContext().getResources().getStringArray(R.array.experienceRanges);
//        List<String> experienceRangesList = new ArrayList<>(Arrays.asList(experienceRanges));

        List<Integer> experienceRangesList = new ArrayList<Integer>();
        List<String> experienceRangesTextList = new ArrayList<>();

//        for (int i = 0; i <= 40; i++) {
//            experienceRangesList.add(i);
//            experienceRangesTextList.add(i + getString(R.string.years));
//        }

        Locale currentLocale = getResources().getConfiguration().getLocales().get(0); // Get the current locale
        for (int i = 0; i <= 40; i++) {
            experienceRangesList.add(i);

            String localizedNumber = currentLocale.getLanguage().equals("bn")
                    ? convertToBengaliNumber(i)
                    : String.valueOf(i);

            String yearsText = getString(R.string.years); // e.g. " বছর" in Bengali, " years" in English
            experienceRangesTextList.add(localizedNumber + yearsText);
        }

        Log.d("ExperienceInputFragment","size of experience list passed: "+experienceRangesTextList.size());

        adapter = new RoleAdapter(requireContext(), experienceRangesTextList);
        binding.spinnerYearsOfExperience.setAdapter(adapter);

        binding.spinnerYearsOfExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RegisterUserModel user = viewModel.getUser();
                user.setYearsOfExperience(experienceRangesList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    private String convertToBengaliNumber(int number) {
        char[] banglaDigits = {'০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'};
        StringBuilder banglaNumber = new StringBuilder();
        for (char digit : String.valueOf(number).toCharArray()) {
            if (Character.isDigit(digit)) {
                banglaNumber.append(banglaDigits[digit - '0']);
            } else {
                banglaNumber.append(digit); // just in case
            }
        }
        return banglaNumber.toString();
    }
}