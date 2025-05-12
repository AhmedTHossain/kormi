package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapter.RoleAdapter;
import com.apptechbd.nibay.auth.domain.model.RegisterUserModel;
import com.apptechbd.nibay.databinding.FragmentEducationInputBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EducationInputFragment extends Fragment {
    private FragmentEducationInputBinding binding;
    private RegistrationViewModel registrationViewModel;
    private RoleAdapter adapter;

    public EducationInputFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEducationInputBinding.inflate(inflater, container, false);

        //Get shared viewmodel using Activity scope
        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        String[] educationLevels = requireContext().getResources().getStringArray(R.array.educationQualifications);
        List<String> educationLevelsList = new ArrayList<>(Arrays.asList(educationLevels));

        adapter = new RoleAdapter(requireContext(), educationLevelsList);
        binding.spinnerEducationLevel.setAdapter(adapter);

        binding.spinnerEducationLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RegisterUserModel user = registrationViewModel.getUser();

                String educationLevelSelected = educationLevelsList.get(position);
                user.setMaxEducationDegreeName(educationLevelSelected);

                registrationViewModel.setUser(user);

                Toast.makeText(requireContext(),"this user's role is: "+user.getRole(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}