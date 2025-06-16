package com.apptechbd.nibay.appmenu.domain.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.nibay.R;
import com.apptechbd.nibay.appmenu.domain.model.FAQ;
import com.apptechbd.nibay.appmenu.presentation.FaqViewModel;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.Holder> {

    private ArrayList<FAQ> faqList;
    private Context context;
    private FaqViewModel viewModel;

    public FaqAdapter(ArrayList<FAQ> faqList, Context context, FaqViewModel viewModel) {
        this.faqList = faqList;
        this.context = context;
        this.viewModel = viewModel;
        Log.d("FaqAdapter","number of FAQs = "+faqList.get(0).getAnswer());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_faq, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getTextQuestion().setText(faqList.get(holder.getAdapterPosition()).getQuestion());
        holder.getTextAnswer().setText(faqList.get(holder.getAdapterPosition()).getAnswer());
        if (faqList.get(holder.getAdapterPosition()).getExpanded()) {
            // Get the new drawable you want to set
            Drawable newDrawable = ContextCompat.getDrawable(context, R.drawable.ic_arrow_up);
            // Set the new drawable to the TextView
            holder.getTextQuestion().setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

            holder.getTextAnswer().setVisibility(View.VISIBLE);
            holder.getView().setVisibility(View.GONE);
        } else {
            // Get the new drawable you want to set
            Drawable newDrawable = ContextCompat.getDrawable(context, R.drawable.ic_arrow_drop_down);
            // Set the new drawable to the TextView
            holder.getTextQuestion().setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

            holder.getTextAnswer().setVisibility(View.GONE);
            holder.getView().setVisibility(View.VISIBLE);
        }

        holder.getLayoutFaq().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onTapFaq(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView textQuestion, textAnswer;
        private LinearLayout layoutFaq;
        private View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textQuestion = itemView.findViewById(R.id.text_ques);
            textAnswer = itemView.findViewById(R.id.text_answer);
            layoutFaq = itemView.findViewById(R.id.layout_faq);
            view = itemView.findViewById(R.id.view);
        }

        public TextView getTextQuestion() {
            return textQuestion;
        }

        public void setTextQuestion(TextView textQuestion) {
            this.textQuestion = textQuestion;
        }

        public TextView getTextAnswer() {
            return textAnswer;
        }

        public void setTextAnswer(TextView textAnswer) {
            this.textAnswer = textAnswer;
        }

        public LinearLayout getLayoutFaq() {
            return layoutFaq;
        }

        public void setLayoutFaq(LinearLayout layoutFaq) {
            this.layoutFaq = layoutFaq;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }
    }
}