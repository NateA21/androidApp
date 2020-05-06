package com.example.idontcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.idontcare.data.SettingsFragment;
import com.example.idontcare.data.model.LoggedInUser;
import com.example.idontcare.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String primaryUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        primaryUserID = getIntent().getStringExtra("userID");
        final Button makeListButton = findViewById(R.id.makeListButton);
        final Button mapsButton = findViewById(R.id.iDontCareButton);

        makeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomActivity = new Intent(MainActivity.this, RandomActivity.class);
                randomActivity.putExtra("userID", primaryUserID);
                startActivity(randomActivity);
            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent mapsActivity = new Intent(MainActivity.this, MapsActivity.class);
                mapsActivity.putExtra("userID", primaryUserID);
                startActivity(mapsActivity);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

                return true;


            default:
                // If we got here, the user's action was not recognized.
                //                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
