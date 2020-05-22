package com.example.smsboomer.ui.stats;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatsViewModel extends ViewModel {

    private MutableLiveData<String> totalSMS;
    private MutableLiveData<String> totalContacts;

    public StatsViewModel() {
        totalSMS = new MutableLiveData<>();
        totalSMS.setValue("SMS total");

        totalContacts = new MutableLiveData<>();
        totalContacts.setValue("Contacts total");
    }

    public LiveData<String> getTotalSMS() { return totalSMS; }

    public LiveData<String> getTotalContacts() { return totalContacts; }
}