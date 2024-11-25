package com.apptechbd.nibay.core.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.apptechbd.nibay.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

public class ProgressDialog {
    public AlertDialog showLoadingDialog(String title, String disclaimerMessageText, Context context) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context); // Use MaterialAlertDialogBuilder for Material Design theme

        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.progress_alert_dialog_loading, null);

        MaterialTextView textDisclaimer = view.findViewById(R.id.text_disclaimer);
        textDisclaimer.setText(disclaimerMessageText);

        MaterialTextView textTitle = view.findViewById(R.id.text_title);
        textTitle.setText(title);

        builder.setView(view);
//        builder.setTitle(title);
        builder.setCancelable(false)
                .setPositiveButton("", null)
                .setNegativeButton("", null);
        return builder.show();
    }
}
