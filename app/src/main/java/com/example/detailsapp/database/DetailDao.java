package com.example.detailsapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DetailDao {
    @Query("SELECT * FROM details")
     LiveData<List<DetailsEntry>> loadAllDetails();

    @Insert
    void insertDetails(DetailsEntry detailsEntry);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void updateDetails(DetailsEntry detailsEntry);

    @Delete
    void deleteTask(DetailsEntry detailsEntry);

    @Query("SELECT * FROM details where id =:id")
    LiveData<DetailsEntry> loadTaskById(int id);

}
