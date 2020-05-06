package com.example.idontcare.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.idontcare.data.model.LoggedInUser;
import com.example.idontcare.data.model.UserDAO;

import java.util.List;
import java.util.concurrent.Future;

public class LoginRepository {
    private UserDAO userDAO;
    private LiveData<List<LoggedInUser>> allUsers;
    private LiveData<LoggedInUser> singleUser;
    private LiveData<Boolean> validLogin;
    private Object LoginCheck;

    LoginRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDAO = db.userDAO();

    }

    LiveData<List<LoggedInUser>> getAllUsers() {
        return allUsers;
    }

    void insert(LoggedInUser user) {
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    //IT WORKS
    private LoggedInUser user;
    LoggedInUser checkLogin(final String displayName, final String password) {
        try {
            UserDatabase.databaseWriteExecutor.submit(new Runnable() {
                @Override
                public void run() {
                   user = userDAO.checkLogin(displayName, password);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }




}