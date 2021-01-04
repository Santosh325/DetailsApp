package com.example.detailsapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.detailsapp.database.AppDatabase;
import com.example.detailsapp.database.DetailsEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DetailsListAdapter.ItemClickListener {
    private RecyclerView mRecyclerView;
    private DetailsListAdapter detailsListAdapter;
    private AppDatabase mDb;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());
        retrieveDetails();
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailsListAdapter = new DetailsListAdapter(this,this);
        mRecyclerView.setAdapter(detailsListAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
              startActivity(intent);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<DetailsEntry> details = detailsListAdapter.getDetails();
                        mDb.detailDao().deleteTask(details.get(position));

                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void retrieveDetails() {
//        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        
        viewModel.getDetails().observe(this, new Observer<List<DetailsEntry>>() {
            @Override
            public void onChanged(List<DetailsEntry> detailsEntries) {
                Log.d("mainact", "updating tasks from  from livedata in viewmodel");
                detailsListAdapter.setTasks(detailsEntries);
            }
        });

    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(MainActivity.this,AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_DETAIL_ID,itemId);
        startActivity(intent);
    }
}