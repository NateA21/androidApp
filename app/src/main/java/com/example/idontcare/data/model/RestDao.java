package com.example.idontcare.data.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RestDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Restaurant restaurant);

    @Delete
    void delete(Restaurant...restaurants);

    @Query("select * from restaurants")
    LiveData<List<Restaurant>> getAllRestaurants();


}
