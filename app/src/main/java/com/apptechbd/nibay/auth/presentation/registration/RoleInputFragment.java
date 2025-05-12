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
import com.apptechbd.nibay.databinding.FragmentRoleInputBinding;

import java.util.Arrays;
import java.util.List;

public class RoleInputFragment extends Fragment {
    private FragmentRoleInputBinding binding;
    private RoleAdapter adapter;
    private RegistrationViewModel viewModel;
    private ViewPager2 viewPager2;

    public RoleInputFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoleInputBinding.inflate(inflater, container, false);

        //Get shared viewmodel using Activity scope
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
//        String[] rolesInEnglish = new StringHelper().getRolesInEnglish(requireContext());
//        String[] rolesInBengali = new StringHelper().getRolesInBengali(requireContext());

        List<String> roleList = Arrays.asList(roles);
        adapter = new RoleAdapter(requireContext(), roleList);

        binding.buttonRole.setAdapter(adapter);
        binding.buttonRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RegisterUserModel user = viewModel.getUser();

                user.setRole(position);

                viewModel.setUser(user);

                Toast.makeText(requireContext(), roles[position] + " position = " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentFragment = viewPager2.getCurrentItem();
                viewModel.goToNextPage(currentFragment);
            }
        });

        return binding.getRoot();
    }
}