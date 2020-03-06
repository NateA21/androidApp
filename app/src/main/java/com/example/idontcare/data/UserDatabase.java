package com.example.idontcare.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.idontcare.data.model.LoggedInUser;
import com.example.idontcare.data.model.UserDAO;

@Database(entities = {LoggedInUser.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public interface LoggedInUserListener {
        void onLoggedInUserReturned(String[] loggedInUser);
    }


    public abstract UserDAO userDAO();

    private static UserDatabase INSTANCE;

    public static synchronized UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user_database").addCallback(createUserDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;

    }

    private static RoomDatabase.Callback createUserDatabaseCallback = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            insert(new LoggedInUser(0, "admin", "password"));
        }
    };

    public static void insert(final LoggedInUser loggedInUser) {
        new AsyncTask<LoggedInUser, Void, Void>() {
            protected Void doInBackground(LoggedInUser...users) {
                INSTANCE.userDAO().insert(loggedInUser);
                return null;
            }
        }.execute(loggedInUser);
    }

    public static void getFavRestaurants(int id, final LoggedInUserListener listener) {
        new AsyncTask<Integer, Void, String[]>() {
            protected String[] doInBackground(Integer...ids) {
                return INSTANCE.userDAO().getFavRestaurants(ids[0]);
            }
            protected void onPostExecute(String[] restaurants) {
                super.onPostExecute(restaurants);
                listener.onLoggedInUserReturned(restaurants);
            }
        }.execute(id);
    }

    public static void delete(LoggedInUser user) {
        new AsyncTask<LoggedInUser, Void, Void> () {
            protected Void doInBackground(LoggedInUser... users) {
                INSTANCE.userDAO().delete(users[0]);
                return null;
            }
        }.execute(user);
    }

    public static void update(LoggedInUser user) {
        new AsyncTask<LoggedInUser, Void, Void> () {
            protected Void doInBackground(LoggedInUser... users) {
                INSTANCE.userDAO().update(users[0]);
                return null;
            }
        }.execute(user);
    }




}
