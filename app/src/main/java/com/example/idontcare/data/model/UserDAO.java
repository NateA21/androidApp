package com.example.idontcare.data.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {


    @Query("SELECT savedAddresses FROM users WHERE users.rowid = :id")
    String[] getSavedAddresses(int id);

    //Used for getting fav restaurants, requires user to be logged in
    @Query("SELECT favRestaurants FROM users WHERE users.rowid = :id")
    String[] getFavRestaurants(int id);

    //LOGIN FUNCTION. NOT SECURE ATM
    @Query("SELECT displayName, password FROM users WHERE users.displayName = :displayname and users.password = :password")
    boolean checkLogin(String displayname, String password);

    @Insert
    void insert(LoggedInUser...users);

    @Update
    void update(LoggedInUser...users);

    @Delete
    void delete(LoggedInUser...users);


}
