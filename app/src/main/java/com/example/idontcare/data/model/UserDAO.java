package com.example.idontcare.data.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {


    @Query("SELECT savedAddresses FROM users WHERE users.rowid = :id")
    String[] getSavedAddresses(int id);

    //Used for getting fav restaurants, requires user to be logged in
    @Query("SELECT favRestaurants FROM users WHERE users.rowid = :id")
    String[] getFavRestaurants(int id);

    //LOGIN FUNCTION. NOT SECURE ATM
    @Query("SELECT rowid, displayName, password FROM users WHERE users.displayName = :displayname and users.password = :password")
    LoggedInUser checkLogin(String displayname, String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LoggedInUser user);

    @Update
    void update(LoggedInUser...users);

    @Delete
    void delete(LoggedInUser...users);

    @Insert
    void createUser(LoggedInUser...users);

    @Query("select * from users")
    LiveData<List<LoggedInUser>> getAllUsers();

    //@Query("SELECT displayName, password FROM users WHERE users.displayName = :displayname and users.password = :password")
    //LiveData<Boolean> checkLogin(String displayname, String password);
}
