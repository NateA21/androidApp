package com.example.idontcare.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

@Entity(tableName="users")
public class LoggedInUser {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int userId;

    @ColumnInfo(name = "displayName")
    private String displayName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "favRestaurants")
    private String[] favRestaurants;

    @ColumnInfo(name = "savedAddresses")
    private String[] savedAddresses;


    public LoggedInUser(int userId, String displayName, String password) {
        this.userId = userId;
        this.displayName = displayName;
        this.password = password;

    }

    public void addAddress(String newAddress) {



    }

    public String[] getSavedAddresses() { return savedAddresses; }

    public String[] getFavRestaurants() { return favRestaurants; }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() { return password; }
}
