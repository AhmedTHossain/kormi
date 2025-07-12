package com.apptechbd.nibay.home.domain.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.DateConverter;
import com.apptechbd.nibay.core.utils.StringUtils;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.home.presentation.HomeViewModel;
import com.bumptech.glide.Glide;

public class JobAdAdapter extends ListAdapter<JobAd, RecyclerView.ViewHolder> {

    private final Context context;
    private final HomeViewModel homeViewModel;
    private final String showJobs;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private boolean showLoadingFooter = false;

    public JobAdAdapter(Context context, HomeViewModel homeViewModel, String showJobs) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.homeViewModel = homeViewModel;
        this.showJobs = showJobs;
    }

    private static final DiffUtil.ItemCallback<JobAd> DIFF_CALLBACK = new DiffUtil.ItemCallback<JobAd>() {
        @Override
        public boolean areItemsTheSame(@NonNull JobAd oldItem, @NonNull JobAd newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull JobAd oldItem, @NonNull JobAd newItem) {
            return oldItem.equals(newItem); // Make sure JobAd overrides equals() properly
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_loading_footer, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_job_ad, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            JobAd jobAd = getItem(position);
            ViewHolder jobHolder = (ViewHolder) holder;

            jobHolder.txtJobTitle.setText(jobAd.getTitle());
            jobHolder.txtCompanyName.setText(jobAd.getEmployerName());

            String location = jobAd.getDistrict() + ", " + jobAd.getDivision();
            jobHolder.txtLocation.setText(location);
            jobHolder.txtExpireDate.setText(new DateConverter().convertToLocalDate(jobAd.getApplicationDeadline()));

            if (showJobs.equals("all")) {
                handleJobStatusDisplay(jobHolder, jobAd.getJobStatus());
            } else {
                handleJobStatusDisplay(jobHolder, jobAd.getApplicationStatus());
            }

            String completeUrl = "https://nibay.co/" + jobAd.getEmployerPhoto();
            Glide.with(context).load(completeUrl).into(jobHolder.imgCompanyLogo);

            jobHolder.txtJobRole.setText(jobAd.getJobRoleTxtBn());

            jobHolder.itemView.setOnClickListener(v -> homeViewModel.onJobClicked(jobAd));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (showLoadingFooter ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingFooter && position == getItemCount() - 1) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    private void handleJobStatusDisplay(ViewHolder holder, String status) {
        if (status == null) {
            holder.txtApplicationStatus.setVisibility(View.GONE);
        } else {
            holder.txtApplicationStatus.setVisibility(View.VISIBLE);
            Drawable background;
            switch (status) {
                case "REJECTED":
                    background = context.getResources().getDrawable(R.drawable.bg_custom_text_error);
                    break;
                case "PENDING":
                    background = context.getResources().getDrawable(R.drawable.bg_text_custom_neutral);
                    break;
                case "OFFERED":
                    background = context.getResources().getDrawable(R.drawable.bg_custom_text_offered);
                    break;
                default:
                    background = null;
            }
            if (background != null) holder.txtApplicationStatus.setBackground(background);
            holder.txtApplicationStatus.setText(new StringUtils().toCamelCase(status));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtJobTitle, txtCompanyName, txtLocation, txtExpireDate, txtApplicationStatus, txtJobRole;
        ImageView imgCompanyLogo;

        ViewHolder(View itemView) {
            super(itemView);
            txtJobTitle = itemView.findViewById(R.id.text_job_title);
            txtCompanyName = itemView.findViewById(R.id.text_company_name);
            txtLocation = itemView.findViewById(R.id.text_location);
            txtExpireDate = itemView.findViewById(R.id.text_expire_date);
            txtApplicationStatus = itemView.findViewById(R.id.text_application_status);
            imgCompanyLogo = itemView.findViewById(R.id.img_company_logo);
            txtJobRole = itemView.findViewById(R.id.text_job_role);
        }
    }

    public void showLoadingFooter(boolean show) {
        this.showLoadingFooter = show;
        notifyDataSetChanged();
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}