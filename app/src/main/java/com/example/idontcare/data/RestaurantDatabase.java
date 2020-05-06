package com.example.idontcare.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.idontcare.data.model.RestDao;
import com.example.idontcare.data.model.Restaurant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities ={Restaurant.class}, version = 1, exportSchema = false)
public abstract class RestaurantDatabase extends RoomDatabase {
    public abstract RestDao restDAO();
    private static RestaurantDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized RestaurantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RestaurantDatabase.class, "restaurant_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
