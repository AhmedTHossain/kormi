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

public class JobAdAdapter extends ListAdapter<JobAd, JobAdAdapter.ViewHolder> {

    private final Context context;
    private final HomeViewModel homeViewModel;
    private final String showJobs;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_ad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobAd jobAd = getItem(position);
        holder.txtJobTitle.setText(jobAd.getTitle());
        holder.txtCompanyName.setText(jobAd.getEmployerName());

        String location = jobAd.getDistrict() + ", " + jobAd.getDivision();
        holder.txtLocation.setText(location);
        holder.txtExpireDate.setText(new DateConverter().convertToLocalDate(jobAd.getApplicationDeadline()));

        if (showJobs.equals("all")) {
            handleJobStatusDisplay(holder, jobAd.getJobStatus());
        } else {
            handleJobStatusDisplay(holder, jobAd.getApplicationStatus());
        }

        String completeUrl = "https://nibay.co/" + jobAd.getEmployerPhoto();
        Glide.with(context).load(completeUrl).into(holder.imgCompanyLogo);

        holder.txtJobRole.setText(jobAd.getJobRoleTxtBn());

        holder.itemView.setOnClickListener(v -> homeViewModel.onJobClicked(jobAd));
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
}