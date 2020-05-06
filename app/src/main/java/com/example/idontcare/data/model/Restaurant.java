package com.example.idontcare.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="restaurants")
public class Restaurant {

    @PrimaryKey()
    @ColumnInfo(name = "rowid")
    private int Id;

    @ColumnInfo(name = "restaurantName")
    private String restaurantName;

    @ColumnInfo(name = "restaurantStyle")
    private String restaurantStyle;

    public Restaurant(int id, String restaurantName, String restaurantStyle) {
        this.Id = id;
        this.restaurantName = restaurantName;
        this.restaurantStyle = restaurantStyle;
    }

    public int getId() {return Id;}

    public String getRestaurantName() {return restaurantName;}
    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}
    public String getRestaurantStyle() {return restaurantStyle;}
    public void setRestaurantStyle(String restaurantStyle) {this.restaurantStyle = restaurantStyle;}

}
