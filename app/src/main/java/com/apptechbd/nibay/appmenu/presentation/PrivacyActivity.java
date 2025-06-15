package com.apptechbd.nibay.appmenu.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.text.HtmlCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.databinding.ActivityPrivacyBinding;

import java.util.Locale;

public class PrivacyActivity extends BaseActivity {
    private ActivityPrivacyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });

        String rawHtmlString = getString(R.string.privacy_policy_body);
        binding.textTermsPrivacy.setText(HtmlCompat.fromHtml(rawHtmlString, HtmlCompat.FROM_HTML_MODE_LEGACY));

    }
}