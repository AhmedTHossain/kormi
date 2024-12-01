package com.apptechbd.nibay.home.domain.adapters;

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
import com.apptechbd.nibay.home.domain.models.JobAd;

import java.util.ArrayList;

public class JobAdAdapter extends RecyclerView.Adapter<JobAdAdapter.ViewHolder> {
    private ArrayList<JobAd> jobAds;
    private Context context;

    public JobAdAdapter(ArrayList<JobAd> jobAds, Context context) {
        this.jobAds = jobAds;
        this.context = context;
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
        holder.getTxtJobTitle().setText(jobAd.getJobTitle());
        holder.getTxtCompanyName().setText(jobAd.getCompanyName());
        holder.getTxtLocation().setText(jobAd.getLocation());
        holder.getTxtExpireDate().setText(jobAd.getExpireDate());
        holder.getTxtApplicationStatus().setText(jobAd.getApplicationStatus());
        holder.getImgCompanyLogo().setImageResource(jobAd.getCompanyLogo());

        Log.e("JobAdAdapter", "Image resource not found: " + jobAd.getCompanyLogo());
    }

    @Override
    public int getItemCount() {
        return jobAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJobTitle, txtCompanyName, txtLocation, txtExpireDate, txtApplicationStatus;
        private ImageView imgCompanyLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJobTitle = itemView.findViewById(R.id.text_job_title);
            txtCompanyName = itemView.findViewById(R.id.text_company_name);
            txtLocation = itemView.findViewById(R.id.text_location);
            txtExpireDate = itemView.findViewById(R.id.text_expire_date);
            txtApplicationStatus = itemView.findViewById(R.id.text_application_status);
            imgCompanyLogo = itemView.findViewById(R.id.img_company_logo);
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
    }
}
