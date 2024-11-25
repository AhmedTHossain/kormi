package com.apptechbd.nibay.auth.presentation.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapters.RoleAdapter;
import com.apptechbd.nibay.databinding.FragmentLocationInputBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationInputFragment extends Fragment {
    private FragmentLocationInputBinding binding;
    private RoleAdapter adapterDivision, adapterDistrict;

    private List<String> districtList;

    public LocationInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationInputBinding.inflate(inflater, container, false);

        // Arrays of divisions and districts
        String[] divisions = requireContext().getResources().getStringArray(R.array.divisions);
        String[] dhakaDistricts = requireContext().getResources().getStringArray(R.array.dhakaDistricts);
        String[] chittagongDistricts = requireContext().getResources().getStringArray(R.array.chittagongDistricts);
        String[] rajshahiDistricts = requireContext().getResources().getStringArray(R.array.rajshahiDistricts);
        String[] khulnaDistricts = requireContext().getResources().getStringArray(R.array.khulnaDistricts);
        String[] barisalDistricts = requireContext().getResources().getStringArray(R.array.barisalDistricts);
        String[] sylhetDistricts = requireContext().getResources().getStringArray(R.array.sylhetDistricts);
        String[] mymensinghDistricts = requireContext().getResources().getStringArray(R.array.mymensinghDistricts);
        String[] rangpurDistricts = requireContext().getResources().getStringArray(R.array.rangpurDistricts);

        List<String> divisionList = new ArrayList<>(Arrays.asList(divisions));
        List<String> districtList = new ArrayList<>(Arrays.asList(requireContext().getResources().getStringArray(R.array.placeholderDistrict)));

        // Set up the adapters
        adapterDivision = new RoleAdapter(requireContext(), divisionList);
        adapterDistrict = new RoleAdapter(requireContext(), districtList);

        binding.spinnerDivision.setAdapter(adapterDivision);
        binding.spinnerDistrict.setAdapter(adapterDistrict);

        // Division spinner listener
        binding.spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtList.clear(); // Clear the existing list

                // Update districtList based on selected division
                if (divisionList.get(position).equals(divisions[0])) {
                    districtList.addAll(Arrays.asList(dhakaDistricts));
                } else if (divisionList.get(position).equals(divisions[1])) {
                    districtList.addAll(Arrays.asList(chittagongDistricts));
                } else if (divisionList.get(position).equals(divisions[2])) {
                    districtList.addAll(Arrays.asList(rajshahiDistricts));
                } else if (divisionList.get(position).equals(divisions[3])) {
                    districtList.addAll(Arrays.asList(khulnaDistricts));
                } else if (divisionList.get(position).equals(divisions[4])) {
                    districtList.addAll(Arrays.asList(barisalDistricts));
                } else if (divisionList.get(position).equals(divisions[5])) {
                    districtList.addAll(Arrays.asList(sylhetDistricts));
                } else if (divisionList.get(position).equals(divisions[6])) {
                    districtList.addAll(Arrays.asList(mymensinghDistricts));
                } else if (divisionList.get(position).equals(divisions[7])) {
                    districtList.addAll(Arrays.asList(rangpurDistricts));
                }

                // Notify adapter of data changes
                adapterDistrict.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        return binding.getRoot();
    }

}