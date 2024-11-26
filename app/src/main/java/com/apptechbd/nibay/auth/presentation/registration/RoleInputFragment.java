package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapters.RoleAdapter;
import com.apptechbd.nibay.auth.domain.models.RegisterUserModel;
import com.apptechbd.nibay.databinding.FragmentRoleInputBinding;

import java.util.Arrays;
import java.util.List;

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
                RegisterUserModel user = registrationViewModel.getUser();

                String roleSelected = rolesInEnglish[position];
                user.setRole(roleSelected);

                registrationViewModel.setUser(user);

                Toast.makeText(requireContext(),rolesInEnglish[position],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

}