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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.idontcare.MainActivity;
import com.example.idontcare.R;
import com.example.idontcare.data.LoginViewModel;
import com.example.idontcare.data.UserDatabase;
import com.example.idontcare.data.model.LoggedInUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        final Button newAccountBUtton = findViewById(R.id.newaccount);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading); /* ? */

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getAllUsers();


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
                    mainActivity.putExtra("userID", primaryUser.getUserId());
                    startActivity(mainActivity);
                } else {
                    Log.i("User Did not log In", "No username found by " + username);
                    TextView loginFail = (TextView)findViewById(R.id.loginfailText);
                    //loginFail.setText(R.string.loginFailString);
                    //loginFail.setTextColor(getResources().getColor(R.color.colorRed));
                    Toast.makeText(LoginActivity.this, "Username or Password Wrong", Toast.LENGTH_SHORT).show();
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

        newAccountBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText usernameEditText = findViewById(R.id.username);
                final EditText passwordEditText = findViewById(R.id.password);
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!username.isEmpty()) {
                    if (isValidPassword(password)) {
                        Toast.makeText(LoginActivity.this, "Valid Password, Account Created. Please reattempt login", Toast.LENGTH_SHORT).show();
                        LoggedInUser user1 = new LoggedInUser(0, username, password);
                        loginViewModel.insert(user1);
                        loginViewModel.getAllUsers();
                        Log.i("inserteduser!!" , ":" + username + password);

                        //Toast.makeText(LoginActivity.this, "Please attempt login", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Password. Need 1 number, 1 lower, 1 upper, 6+ length", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }






}
