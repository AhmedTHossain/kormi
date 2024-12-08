package com.apptechbd.nibay.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.databinding.FragmentProfileBinding;
import com.apptechbd.nibay.home.domain.adapters.EmployerRatingAdapter;
import com.apptechbd.nibay.home.domain.adapters.JobAdAdapter;
import com.apptechbd.nibay.home.domain.models.EmployerRating;
import com.apptechbd.nibay.home.domain.models.JobAd;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private EmployerRatingAdapter adapter;
    private ArrayList<EmployerRating> employerRatings = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        createDummyEmployerRatings();

        adapter = new EmployerRatingAdapter(employerRatings, requireContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);

        return binding.getRoot();
    }

    private void createDummyEmployerRatings(){
        // Clear existing data
        employerRatings.clear();

        // Add job ads with sample data
        employerRatings.add(new EmployerRating("এনা ট্রান্সপোর্ট লিমিটেড", "লোরেম ইপসাম ডলর সিট আমেট, কনসেকটেটুর অ্যাডিপিসিং এলিট। সেড ডো ইইউসিমড টেম্পোর ইনসিডিডুন্ট ইউট ল্যাবোর এ ডোলোর ম্যাগনা অ্যালিকুয়া।", "https://drive.google.com/uc?id=1OrUIr35ocmqQv8_PbQ0Qnx67TOsy1Buw", 4));
        employerRatings.add(new EmployerRating("সৈয়দ আবদুল্লাহ", "লোরেম ইপসাম ডলর সিট আমেট, কনসেকটেটুর অ্যাডিপিসিং এলিট।", null, 5));
    }
}