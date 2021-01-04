package com.example.detailsapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.detailsapp.database.AppDatabase;
import com.example.detailsapp.database.DetailsEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    LiveData<List<DetailsEntry>> details;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from database");
        details = database.detailDao().loadAllDetails();
    }
    public LiveData<List<DetailsEntry>> getDetails() {
        return details;
    }
}
