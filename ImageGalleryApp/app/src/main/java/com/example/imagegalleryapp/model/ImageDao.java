package com.example.imagegalleryapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM ImageData")
    List<ImageData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ImageData imageData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ImageData> imageData);

    @Delete
    void delete(ImageData imageData);

    @Update
    void update(ImageData imageData);
}
