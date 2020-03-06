package com.example.idontcare.data.model;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Class for user login and database.
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
        this.favRestaurants = new String[10];
        this.savedAddresses = new String[10];
    }

    public void addRestaurant(String restaurant) {
        for (int i = 0; i < this.favRestaurants.length; i++) {
            if (this.favRestaurants[i] == null || this.favRestaurants[i].equals("")) {
                this.favRestaurants[i] = restaurant;
                return;
            }

        }
        Log.e("Full String[]", "Full Restaurant String Array for UserID given.");
    }

    public void addAddress(String address) {
        for (int i = 0; i < this.savedAddresses.length; i++) {
            if (this.savedAddresses[i] == null || this.savedAddresses[i].equals("")) {
                this.savedAddresses[i] = address;
                return;
            }

        }
        Log.e("Full String[]", "Full Address String Array for UserID given.");
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
