package com.apptechbd.nibay.appmenu.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.appmenu.domain.adapter.FaqAdapter;
import com.apptechbd.nibay.appmenu.domain.model.FAQ;
import com.apptechbd.nibay.core.utils.BaseActivity;
import com.apptechbd.nibay.databinding.ActivityFaqBinding;

import java.util.ArrayList;
import java.util.Locale;

public class FaqActivity extends BaseActivity {
    private ActivityFaqBinding binding;
    private ArrayList<FAQ> faqArrayList = new ArrayList<>();
    private FaqAdapter adapter;
    private FaqViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFaqBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveLocale("bn");
        setLocale(new Locale("bn"));

        initViewModel();
        expandFaqAnswer();

        // Handle navigation icon click
        binding.topAppBar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed(); //navigate back
        });

        String[] faqQuesList = getResources().getStringArray(R.array.faq_questions_list);
        String[] faqAnswerList = getResources().getStringArray(R.array.faq_answers_list);
        for (int i = 0; i < faqQuesList.length; i++) {
            FAQ faq = new FAQ(faqQuesList[i], faqAnswerList[i]);
            if (i == 0)
                faq.setExpanded(true);
            else
                faq.setExpanded(false);
            faqArrayList.add(faq);
        }
        adapter = new FaqAdapter(faqArrayList, this, viewModel);
        binding.recyclerviewFaq.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewFaq.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(FaqViewModel.class);
    }

    private void expandFaqAnswer() {
        viewModel.getFaqTapped().observe(this, faqTapped -> {
            boolean expanded = !faqArrayList.get(faqTapped).getExpanded();
            faqArrayList.get(faqTapped).setExpanded(expanded);
            adapter.notifyDataSetChanged();
        });
    }
}