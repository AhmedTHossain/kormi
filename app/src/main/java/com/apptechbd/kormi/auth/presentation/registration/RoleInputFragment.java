package com.apptechbd.kormi.auth.presentation.registration;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.auth.domain.adapters.RoleAdapter;
import com.apptechbd.kormi.auth.domain.models.RegisterUserModel;
import com.apptechbd.kormi.databinding.FragmentRoleInputBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RoleInputFragment extends Fragment {
    private FragmentRoleInputBinding binding;
    private RoleAdapter adapter;
    private RegistrationViewModel registrationViewModel;

    public RoleInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoleInputBinding.inflate(inflater, container, false);

        //Get shared viewmodel using Activity scope
        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        String[] rolesInEnglish = {"Driver","Checker","Counter Master","Foreman","GM (General Manager)","Helper","Manager","Mechanic/Mistry","Supervisor/Passenger Guide"};

        List<String> roleList = Arrays.asList(roles);
        adapter = new RoleAdapter(requireContext(),roleList);

        binding.buttonRole.setAdapter(adapter);
        binding.buttonRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RegisterUserModel registerUserModel = new RegisterUserModel();

                String roleSelected = rolesInEnglish[position];
                registerUserModel.setRole(roleSelected);

                Toast.makeText(requireContext(),rolesInEnglish[position],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

}