package com.example.idontcare.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.idontcare.data.model.LoggedInUser;
import com.example.idontcare.data.model.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LoggedInUser.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public interface LoggedInUserListener {
        void onLoggedInUserReturned(String[] loggedInUser);
    }


    public abstract UserDAO userDAO();

    private static UserDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static synchronized UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDAO dao = INSTANCE.userDAO();
                //dao.deleteAll();

                LoggedInUser user = new LoggedInUser(0, "admin", "password");
                dao.insert(user);
                user = new LoggedInUser(0, "nate", "12345");
                dao.insert(user);
            });
        }
    };






    /*
    public static void insert(final LoggedInUser user) {
        new AsyncTask<LoggedInUser, Void, Void>() {
            protected Void doInBackground(LoggedInUser...users) {
                INSTANCE.userDAO().insert(user);
                return null;
            }
        }.execute(user);
    }

    public static void getFavRestaurants(int id, final UserDatabase.LoggedInUserListener listener) {
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


*/


}
