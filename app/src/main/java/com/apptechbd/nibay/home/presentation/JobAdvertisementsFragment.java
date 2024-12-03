package com.apptechbd.nibay.home.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.FragmentJobAdvertisementsBinding;
import com.apptechbd.nibay.home.domain.adapters.RoleAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class JobAdvertisementsFragment extends Fragment {
    private FragmentJobAdvertisementsBinding binding;
    private RoleAdapter adapter;

    public JobAdvertisementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentJobAdvertisementsBinding.inflate(inflater, container, false);

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);
        ArrayList<String> roleList = new ArrayList<>(Arrays.asList(roles));

        adapter = new RoleAdapter(roleList, requireContext());

        binding.recyclerviewRoles.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerviewRoles.setAdapter(adapter);

       return binding.getRoot();
    }
}