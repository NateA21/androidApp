package com.example.idontcare.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.idontcare.MainActivity;
import com.example.idontcare.R;
import com.example.idontcare.data.LoginViewModel;
import com.example.idontcare.data.UserDatabase;
import com.example.idontcare.data.model.LoggedInUser;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

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

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getAllUsers();

        //LoggedInUser user1 = new LoggedInUser(0, "nate", "tobias21");
        //loginViewModel.insert(user1);
        //Log.i("inserteduser!!" , "nate");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                Log.i("UserLogin Check", "Username, Password: " + username + password);
                LoggedInUser primaryUser = loginViewModel.checkLogin(username, password);
                if (primaryUser != null) {
                    Log.i("User Logged In", "User: " + username);

                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    mainActivity.putExtra("user", (Parcelable) primaryUser);
                    startActivity(mainActivity);
                } else {
                    Log.i("User Did not log In", "No username found by " + username);
                    TextView loginFail = (TextView)findViewById(R.id.loginfailText);
                    loginFail.setText(R.string.loginFailString);
                    loginFail.setTextColor(getResources().getColor(R.color.colorRed));
                }
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




}
