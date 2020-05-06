package com.example.idontcare.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.idontcare.data.model.LoggedInUser;
import com.example.idontcare.data.model.RestDao;
import com.example.idontcare.data.model.Restaurant;

import java.util.List;

public class RestaurantRepository {
    private RestDao restDAO;
    private LiveData<List<Restaurant>> allRestaurants;


    RestaurantRepository(Application application) {
        RestaurantDatabase db = RestaurantDatabase.getDatabase(application);
        restDAO = db.restDAO();
    }

    LiveData<List<Restaurant>> getAllRestaurants() {return allRestaurants;}

    void insert(Restaurant restaurant) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            restDAO.insert(restaurant);
        });
    }




}
