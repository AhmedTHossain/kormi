package com.apptechbd.nibay.home.domain.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.DateConverter;
import com.apptechbd.nibay.core.utils.StringUtils;
import com.apptechbd.nibay.home.domain.model.JobAd;
import com.apptechbd.nibay.home.presentation.HomeViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class JobAdAdapter extends RecyclerView.Adapter<JobAdAdapter.ViewHolder> {
    private ArrayList<JobAd> jobAds;
    private Context context;
    private HomeViewModel homeViewModel;

    public JobAdAdapter(ArrayList<JobAd> jobAds, Context context, HomeViewModel homeViewModel) {
        this.jobAds = jobAds;
        this.context = context;
        this.homeViewModel = homeViewModel;
    }

    @NonNull
    @Override
    public JobAdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_ad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdAdapter.ViewHolder holder, int position) {
        JobAd jobAd = jobAds.get(position);
        holder.getTxtJobTitle().setText(jobAd.getTitle());
        holder.getTxtCompanyName().setText(jobAd.getEmployerName());

        String location = jobAd.getDistrict() + ", " + jobAd.getDivision();
        holder.getTxtLocation().setText(location);

        holder.getTxtExpireDate().setText(new DateConverter().convertToLocalDate(jobAd.getApplicationDeadline()));
        if (jobAd.getApplicationStatus() == null)
            holder.getTxtApplicationStatus().setVisibility(View.GONE);
        else {
            if (jobAd.getJobStatus() != null) {
                switch (jobAd.getJobStatus()) {
                    case "REJECTED":
                        holder.getTxtApplicationStatus().setBackground(context.getResources().getDrawable(R.drawable.bg_custom_text_error));
                        break;
                    case "PENDING":
                        holder.getTxtApplicationStatus().setBackground(context.getResources().getDrawable(R.drawable.bg_text_custom_neutral));
                        break;
                    case "OFFERED":
                        holder.getTxtApplicationStatus().setBackground(context.getResources().getDrawable(R.drawable.bg_custom_text_offered));
                        break;
                }
                holder.getTxtApplicationStatus().setText(new StringUtils().toCamelCase(jobAd.getJobStatus()));
            } else
                holder.getTxtApplicationStatus().setVisibility(View.GONE);
        }

        String completeUrl = "https://nibay.co/" + jobAd.getEmployerPhoto();

        Log.d("FollowedEmployerAdapter", "company logo: " + completeUrl);
        Glide.with(context).load(completeUrl).into(holder.getImgCompanyLogo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.onJobClicked(jobAd);
            }
        });

        holder.getTxtJobRole().setText(jobAd.getJobRoleTxtBn());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                homeViewModel.onJobClicked(jobAd.getId());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return jobAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJobTitle, txtCompanyName, txtLocation, txtExpireDate, txtApplicationStatus, txtJobRole;
        private ImageView imgCompanyLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJobTitle = itemView.findViewById(R.id.text_job_title);
            txtCompanyName = itemView.findViewById(R.id.text_company_name);
            txtLocation = itemView.findViewById(R.id.text_location);
            txtExpireDate = itemView.findViewById(R.id.text_expire_date);
            txtApplicationStatus = itemView.findViewById(R.id.text_application_status);
            imgCompanyLogo = itemView.findViewById(R.id.img_company_logo);
            txtJobRole = itemView.findViewById(R.id.text_job_role);
        }

        public TextView getTxtJobTitle() {
            return txtJobTitle;
        }

        public TextView getTxtCompanyName() {
            return txtCompanyName;
        }

        public TextView getTxtLocation() {
            return txtLocation;
        }

        public TextView getTxtExpireDate() {
            return txtExpireDate;
        }

        public TextView getTxtApplicationStatus() {
            return txtApplicationStatus;
        }

        public ImageView getImgCompanyLogo() {
            return imgCompanyLogo;
        }

        public TextView getTxtJobRole() {
            return txtJobRole;
        }
    }
}
