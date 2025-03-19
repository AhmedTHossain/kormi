package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapter.RoleAdapter;
import com.apptechbd.nibay.databinding.FragmentExperienceInputBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExperienceInputFragment extends Fragment {
    private FragmentExperienceInputBinding binding;
    private RoleAdapter adapter;

    public ExperienceInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExperienceInputBinding.inflate(inflater, container, false);

        String[] experienceRanges = requireContext().getResources().getStringArray(R.array.experienceRanges);
        List<String> experienceRangesList = new ArrayList<>(Arrays.asList(experienceRanges));

        adapter = new RoleAdapter(requireContext(), experienceRangesList);
        binding.spinnerYearsOfExperience.setAdapter(adapter);

        binding.spinnerYearsOfExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}