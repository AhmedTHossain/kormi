package com.apptechbd.nibay.home.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.auth.domain.adapter.RoleAdapter;
import com.apptechbd.nibay.core.utils.HelperClass;
import com.apptechbd.nibay.databinding.FragmentJobAdvertisementsBinding;
import com.apptechbd.nibay.home.domain.adapter.FollowedEmployerAdapter;
import com.apptechbd.nibay.home.domain.adapter.JobAdAdapter;
import com.apptechbd.nibay.home.domain.model.FollowedEmployer;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.home.domain.model.PaginatedJobAdResponse;
import com.apptechbd.nibay.jobads.presentation.JobAdvertisementDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobAdvertisementsFragment extends Fragment {

    private FragmentJobAdvertisementsBinding binding;
    private RoleAdapter roleAdapter;
    private FollowedEmployerAdapter followedEmployerAdapter;
    private JobAdAdapter jobAdAdapter;
    private final ArrayList<FollowedEmployer> followedEmployers = new ArrayList<>();
    private HomeViewModel homeViewModel;
    private final HelperClass helperClass = new HelperClass();
    private String currentSelectedEmployerId;
    private ActivityResultLauncher<Intent> jobDetailsLauncher;
    private boolean hasInitialSpinnerSelectionFired = false;
    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupActivityResultLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJobAdvertisementsBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        initRecyclerViews();
        initRoleSpinner();
        observeViewModel();

        homeViewModel.getFollowedEmployers();
        currentPage = 1;
        isLastPage = false;

        startShimmer();
        fetchJobs(currentPage, true);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("JobAdvertisementsFragment", "onResume() called");

        selectDefaultRole();

        if (isAdded()) {
            followedEmployers.clear();
            followedEmployers.addAll(helperClass.getFollowedEmployers(requireContext()));
            for (FollowedEmployer employer : followedEmployers) {
                employer.setSelected(employer.getId().equals(currentSelectedEmployerId));
            }
            setupFollowedEmployers();
        }

        currentPage = 1;
        isLastPage = false;

        startShimmer();
        fetchJobs(currentPage, true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        followedEmployerAdapter = null;
        binding = null;
    }

    private void setupActivityResultLauncher() {
        jobDetailsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String employerId = result.getData().getStringExtra("employerId");
                        boolean isFollowing = result.getData().getBooleanExtra("isFollowing", false);
                        updateEmployerFollowStatus(employerId, isFollowing);
                    }
                }
        );
    }

    private void initRecyclerViews() {
        Context context = getContext();
        if (context == null) return;

        jobAdAdapter = new JobAdAdapter(context, homeViewModel, "all");
        binding.recyclerviewJobAds.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerviewJobAds.setAdapter(jobAdAdapter);

        binding.recyclerviewJobAds.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        currentPage++;
                        fetchJobs(currentPage, false);
                    }
                }
            }
        });

    }

    private void initRoleSpinner() {
        Context context = getContext();
        if (context == null) return;

        ArrayList<String> roles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.roles)));
        roleAdapter = new RoleAdapter(context, roles);
        binding.spinnerRole.setAdapter(roleAdapter);
        selectDefaultRole();

        binding.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                if (!hasInitialSpinnerSelectionFired) {
                    hasInitialSpinnerSelectionFired = true;
                    return;
                }

                if (selectedView instanceof TextView) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.md_theme_primary));
                }

                startShimmer();
                currentPage = 1;
                isLastPage = false;
                fetchJobs(currentPage, true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadFilteredJobAds() {
        int selectedRoleIndex = binding.spinnerRole.getSelectedItemPosition();
        String selectedRole = String.valueOf(selectedRoleIndex);
        boolean isAllRoles = selectedRoleIndex == roleAdapter.getCount() - 1;

        if (currentSelectedEmployerId != null && !isAllRoles) {
            homeViewModel.loadCompanyRoleJobAdvertisements("1", currentSelectedEmployerId, selectedRole);
        } else if (currentSelectedEmployerId != null) {
            homeViewModel.loadCompanyJobAdvertisements("1", currentSelectedEmployerId);
        } else if (!isAllRoles) {
            homeViewModel.loadRoleBasedJobAdvertisements("1", selectedRole);
        } else {
            homeViewModel.loadInitialJobAdvertisements();
        }
    }

    private void observeViewModel() {
        homeViewModel.displayedJobAds.observe(getViewLifecycleOwner(), jobAds -> {
            jobAdAdapter.submitList(jobAds);
            binding.layoutJobAdShimmer.stopShimmerAnimation();
            binding.layoutJobAdShimmer.setVisibility(View.GONE);

            boolean isEmpty = jobAds == null || jobAds.isEmpty();
            binding.recyclerviewJobAds.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            binding.layoutNoJobs.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            binding.layoutJobAd.setVisibility(View.VISIBLE);
        });

        homeViewModel.jobClicked.observe(getViewLifecycleOwner(), job -> {
            if (job != null && isAdded()) {
                openJobDetails(job.getEmployerName(), job.getId(), job.getEmployerPhoto());
            }
        });

        homeViewModel.isFollowedEmployersFetched.observe(getViewLifecycleOwner(), isFetched -> {
            if (Boolean.TRUE.equals(isFetched) && isAdded()) {
                followedEmployers.clear();
                followedEmployers.addAll(helperClass.getFollowedEmployers(requireContext()));
                setupFollowedEmployers();
            } else if (isAdded()) {
                helperClass.showSnackBar(binding.getRoot(), getString(R.string.no_employers_followed_disclaimer_text));
            }
        });

        homeViewModel.followedCompanyClicked.observe(getViewLifecycleOwner(), companyId -> {
            currentSelectedEmployerId = companyId != null && companyId.equals(currentSelectedEmployerId) ? null : companyId;

            for (FollowedEmployer employer : followedEmployers) {
                employer.setSelected(employer.getId().equals(currentSelectedEmployerId));
            }

            if (followedEmployerAdapter != null) {
                followedEmployerAdapter.notifyDataSetChanged();
            }

            startShimmer();
            currentPage = 1;
            isLastPage = false;
            fetchJobs(currentPage, true);

        });
    }

    private void updateEmployerFollowStatus(String employerId, boolean isFollowing) {
        if (employerId == null || getContext() == null) return;

        boolean changed = false;

        if (isFollowing) {
            if (followedEmployers.stream().noneMatch(e -> employerId.equals(e.getId()))) {
                FollowedEmployer newEmployer = helperClass.getEmployerById(getContext(), employerId);
                if (newEmployer != null) {
                    followedEmployers.add(newEmployer);
                    changed = true;
                }
            }
        } else {
            changed = followedEmployers.removeIf(e -> employerId.equals(e.getId()));
            if (employerId.equals(currentSelectedEmployerId)) {
                currentSelectedEmployerId = null;
                homeViewModel.loadInitialJobAdvertisements();
            }
        }

        if (changed) {
            // ✅ Save the updated list to SharedPreferences
            helperClass.saveFollowedEmployers(requireContext(), followedEmployers);

            setupFollowedEmployers();
        }
    }


    private void setupFollowedEmployers() {
        if (!isAdded() || getContext() == null || binding == null) return;

        followedEmployerAdapter = new FollowedEmployerAdapter(followedEmployers, getContext(), homeViewModel);
        binding.recyclerviewFollowedCompanies.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerviewFollowedCompanies.setAdapter(followedEmployerAdapter);

        binding.layoutFollowedEmployer.setVisibility(followedEmployers.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void startShimmer() {
        binding.layoutJobAdShimmer.setVisibility(View.VISIBLE);
        binding.layoutJobAdShimmer.startShimmerAnimation();
        binding.layoutJobAd.setVisibility(View.GONE);
        binding.layoutNoJobs.setVisibility(View.GONE);
    }

    private void openJobDetails(String employer, String id, String logo) {
        if (!isAdded()) return;

        Intent intent = new Intent(getContext(), JobAdvertisementDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("employer", employer);
        intent.putExtra("logo", logo);
        jobDetailsLauncher.launch(intent);
    }

    private void selectDefaultRole() {
        if (binding == null || roleAdapter == null) return;

        int lastIndex = roleAdapter.getCount() - 1;
        if (lastIndex >= 0) {
            binding.spinnerRole.setSelection(lastIndex, true);
        }
    }

    private void fetchJobs(int page, boolean isInitialLoad) {
        isLoading = true;
        jobAdAdapter.showLoadingFooter(!isInitialLoad);

        int selectedRoleIndex = binding.spinnerRole.getSelectedItemPosition();
        String selectedRole = String.valueOf(selectedRoleIndex);
        boolean isAllRoles = selectedRoleIndex == roleAdapter.getCount() - 1;

        LiveData<PaginatedJobAdResponse> liveData;
        if (currentSelectedEmployerId != null && !isAllRoles) {
            liveData = homeViewModel.getCompanyRoleJobAds(String.valueOf(page), currentSelectedEmployerId, selectedRole);
        } else if (currentSelectedEmployerId != null) {
            liveData = homeViewModel.getCompanyJobAds(String.valueOf(page), currentSelectedEmployerId);
        } else if (!isAllRoles) {
            liveData = homeViewModel.getRoleJobAds(String.valueOf(page), selectedRole);
        } else {
            liveData = homeViewModel.getAllJobAds(String.valueOf(page));
        }

//        liveData.observe(getViewLifecycleOwner(), response -> {
//            isLoading = false;
//            jobAdAdapter.showLoadingFooter(false);
//
//            if (response == null || response.getJobAds().isEmpty()) {
//                isLastPage = true;
//                return;
//            }
//
//            if (isInitialLoad) {
//                jobAdAdapter.submitList(response.getJobAds());
//            } else {
//                List<JobAd> currentList = new ArrayList<>(jobAdAdapter.getCurrentList());
//                currentList.addAll(response.getJobAds());
//                jobAdAdapter.submitList(currentList);
//            }
//
//            totalPages = response.getTotalPages();
//            currentPage = response.getCurrentPage();
//            isLastPage = currentPage >= totalPages;
//        });

        liveData.observe(getViewLifecycleOwner(), response -> {
            isLoading = false;
            jobAdAdapter.showLoadingFooter(false);

            binding.layoutJobAdShimmer.stopShimmerAnimation();
            binding.layoutJobAdShimmer.setVisibility(View.GONE);

            List<JobAd> newJobs = response != null ? response.getJobAds() : null;

            if (newJobs == null || newJobs.isEmpty()) {
                isLastPage = true;
                binding.recyclerviewJobAds.setVisibility(View.GONE);
                binding.layoutNoJobs.setVisibility(View.VISIBLE);
                binding.layoutJobAd.setVisibility(View.VISIBLE);
                return;
            }

            if (isInitialLoad) {
                jobAdAdapter.submitList(newJobs);
            } else {
                List<JobAd> currentList = new ArrayList<>(jobAdAdapter.getCurrentList());
                currentList.addAll(newJobs);
                jobAdAdapter.submitList(currentList);
            }

            totalPages = response.getTotalPages();
            currentPage = response.getCurrentPage();
            isLastPage = currentPage >= totalPages;

            binding.recyclerviewJobAds.setVisibility(View.VISIBLE);
            binding.layoutNoJobs.setVisibility(View.GONE);
            binding.layoutJobAd.setVisibility(View.VISIBLE);
        });

    }
}
