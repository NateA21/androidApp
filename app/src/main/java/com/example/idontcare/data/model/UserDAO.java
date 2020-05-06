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


}
