package com.example.smsboomer.ui.params;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParamsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ParamsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is params fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}