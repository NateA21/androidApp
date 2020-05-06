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
    private LoggedInUser primaryUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button makeListButton = findViewById(R.id.makeListButton);
        final Button iDontCareButton = findViewById(R.id.iDontCareButton);
        //primaryUser = getIntent().getExtras("user");


        makeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mainActivity.putExtra("user", (Parcelable) primaryUser);
                //Send to trevors activity
            }
        });

        iDontCareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mainActivity.putExtra("user", (Parcelable) primaryUser);
                //Send to Mikes activity

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
                return super.onOptionsItemSelected(item);

        }
    }
}
