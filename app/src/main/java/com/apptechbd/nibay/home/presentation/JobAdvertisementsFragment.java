package com.apptechbd.nibay.home.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.FragmentJobAdvertisementsBinding;

public class JobAdvertisementsFragment extends Fragment {
    private FragmentJobAdvertisementsBinding binding;
    public JobAdvertisementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentJobAdvertisementsBinding.inflate(inflater, container, false);


       return binding.getRoot();
    }
}