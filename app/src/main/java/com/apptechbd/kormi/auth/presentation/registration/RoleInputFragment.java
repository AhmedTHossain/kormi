package com.apptechbd.kormi.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.auth.domain.adapters.RoleAdapter;
import com.apptechbd.kormi.databinding.FragmentRoleInputBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RoleInputFragment extends Fragment {
    private FragmentRoleInputBinding binding;
    private RoleAdapter adapter;

    public RoleInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoleInputBinding.inflate(inflater, container, false);

        String[] roles = requireContext().getResources().getStringArray(R.array.roles);

        List<String> roleList = Arrays.asList(roles);
        adapter = new RoleAdapter(requireContext(),roleList);

        binding.buttonRole.setAdapter(adapter);

        return binding.getRoot();
    }

}