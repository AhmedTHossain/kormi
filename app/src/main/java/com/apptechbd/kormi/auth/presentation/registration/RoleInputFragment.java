package com.apptechbd.kormi.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.apptechbd.kormi.R;
import com.apptechbd.kormi.auth.domain.adapters.RoleAdapter;
import com.apptechbd.kormi.databinding.FragmentRoleInputBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
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

        binding.buttonRole.setOnClickListener(v -> {
            Log.d("RoleInputFragment", "Button clicked");
            showBottomSheetDialog();
        });

        return binding.getRoot();
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);

        bottomSheet.setContentView(R.layout.bottom_sheet_roles);

        // Get the string array from resources
        String[] roles = requireContext().getResources().getStringArray(R.array.roles);

        // Convert the array to an ArrayList
        ArrayList<String> jobTitlesList = new ArrayList<>(Arrays.asList(roles));
        RecyclerView recyclerView = bottomSheet.findViewById(R.id.recyclerview_roles);
        adapter = new RoleAdapter(requireContext(),jobTitlesList);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }

        bottomSheet.show();
    }
}