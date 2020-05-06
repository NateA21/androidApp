package com.example.idontcare.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.idontcare.data.model.LoggedInUser;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;
    private LiveData<List<LoggedInUser>> allUsers;
    private LiveData<Boolean> validLogin;
    private LoggedInUser validLogin1;


    public LoginViewModel(@NonNull Application application) {
        super(application);

        repository = new LoginRepository(application);
        allUsers = repository.getAllUsers();
    }

    public void insert(LoggedInUser user) {
        repository.insert(user);
    }

    public LiveData<List<LoggedInUser>> getAllUsers() {
        return allUsers;
    }

   // public boolean checkLogin(String displayName, String password) {
    //    return validLogin;
    //}

    public LoggedInUser checkLogin(String displayName, String password) {
        validLogin1 = repository.checkLogin(displayName, password);
        return validLogin1;
    }
}
