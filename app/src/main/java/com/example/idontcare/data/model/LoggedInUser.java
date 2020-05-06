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

    public LoggedInUser(int userId, String displayName, String password) {
        this.userId = userId;
        this.displayName = displayName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() { return password; }
}
