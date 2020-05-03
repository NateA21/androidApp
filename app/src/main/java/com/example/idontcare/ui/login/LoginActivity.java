package com.example.idontcare.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.idontcare.MainActivity;
import com.example.idontcare.R;
import com.example.idontcare.SettingsActivity;
import com.example.idontcare.data.UserDatabase;
import com.example.idontcare.data.loginResponse;

public class LoginActivity extends AppCompatActivity implements loginResponse {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button guestButton = findViewById(R.id.login2);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading); /* ? */

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                UserDatabase.checkLogin(username, password);
                /* Database function goes here */
                /* Some kinda bundle thing here for maps functionality or w/e? */

            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* goes to activity_main.xml from here */
                Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }


    public void login() {

    }

    @Override
    public void loginFinish(Boolean loginFinish) {
        //Where the result of login will go
        if(loginFinish) {
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
        } else {
            //Not a login!
            //Create new login, and then do it anyway for now.
            final EditText usernameEditText = findViewById(R.id.username);
            String username = usernameEditText.getText().toString().trim();
            final EditText passwordEditText = findViewById(R.id.password);
            String password = passwordEditText.getText().toString().trim();
            Log.i("loginFinish: " , "Did not login, creating new login now!");
            UserDatabase.createUser(username, password);
            //Log.i("LoginTry#2" , "Trying to login with newly created account.");
        }
    }
}
