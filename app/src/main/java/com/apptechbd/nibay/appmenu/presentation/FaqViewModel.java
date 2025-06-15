package com.apptechbd.nibay.appmenu.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class FaqViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> faqTapped = new MutableLiveData<>();

    public FaqViewModel(@NonNull Application application) {
        super(application);
    }

    public void onTapFaq(int faqPosition){
        faqTapped.setValue(faqPosition);
    }

    public MutableLiveData<Integer> getFaqTapped() {
        return faqTapped;
    }
}
