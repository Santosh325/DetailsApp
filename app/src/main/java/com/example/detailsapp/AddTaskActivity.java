package com.example.detailsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.detailsapp.database.AppDatabase;
import com.example.detailsapp.database.DetailsEntry;

public class AddTaskActivity extends AppCompatActivity {
    private AppDatabase mDb;
    public static final String EXTRA_DETAIL_ID = "extraDetailId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_DETAIL_ID = "instanceDetailId";
    // Constants for priority
    private int mDetailId = DEFAULT_DETAIL_ID;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_DETAIL_ID = -1;
    // Constant for logging
    Button mButton;
    private static final String TAG = AddTaskActivity.class.getSimpleName();
    private EditText getMEditTextName, getMEditTextFatherName, getMEditTextPhone, getMEditTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_DETAIL_ID)) {
            mDetailId = savedInstanceState.getInt(INSTANCE_DETAIL_ID, DEFAULT_DETAIL_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DETAIL_ID)) {

            if (mDetailId == DEFAULT_DETAIL_ID) {
                mDetailId = intent.getIntExtra(EXTRA_DETAIL_ID,DEFAULT_DETAIL_ID);
                final LiveData<DetailsEntry> details = mDb.detailDao().loadTaskById(mDetailId);
                details.observe(this, new Observer<DetailsEntry>() {
                    @Override
                    public void onChanged(DetailsEntry detailsEntry) {
                        Log.d(TAG, "Receiving database update from live data");
                      details.removeObserver(this);
                        populatUi(detailsEntry);
                    }
                });
   
                // populate the UI
            }
        }
        getMEditTextName = findViewById(R.id.edit_text_name);
        getMEditTextFatherName = findViewById(R.id.edit_text_fatherName);
        getMEditTextPhone = findViewById(R.id.edit_text_phone);
        getMEditTextAddress = findViewById(R.id.edit_text_address);
        getMEditTextName.requestFocus();
    }

    private void populatUi(DetailsEntry detailsEntry) {
        if(detailsEntry == null) {
            return;
        }
        getMEditTextName.setText(detailsEntry.getName());
        getMEditTextFatherName.setText(detailsEntry.getFathersName());
        getMEditTextPhone.setText(detailsEntry.getPhoneNumber());
        getMEditTextAddress.setText(detailsEntry.getAddress());
    }

    public void addDetails(View view) {
        String name = getMEditTextName.getText().toString().trim();
        String fatherName = getMEditTextFatherName.getText().toString().trim();
        String phoneNumber = getMEditTextPhone.getText().toString().trim();
        String address = getMEditTextAddress.getText().toString().trim();
         DetailsEntry detailsEntry = new DetailsEntry(name,fatherName,phoneNumber,address);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if(mDetailId == DEFAULT_DETAIL_ID) {
                        mDb.detailDao().insertDetails(detailsEntry);
                    } else {
                        detailsEntry.setId(mDetailId);
                        mDb.detailDao().updateDetails(detailsEntry);
                    }
                    finish();
                }
            });
        }



}