package com.example.idontcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.idontcare.data.SettingsFragment;
import com.example.idontcare.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String primaryUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button makeListButton = findViewById(R.id.makeListButton);
        final Button iDontCareButton = findViewById(R.id.iDontCareButton);
        primaryUser = getIntent().getStringExtra("user");


        makeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send to trevors activity
            }
        });

        iDontCareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send to Mikes activity

            }
        });

    }

    public void goHome(View view) {
        Intent intent = new Intent (MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public void pickRandom(View view) {
        String place1 = ((EditText) findViewById(R.id.editText)).getText().toString();
        int weight1 = Integer.parseInt(((EditText) findViewById(R.id.editText3)).getText().toString());
        String place2 = ((EditText) findViewById(R.id.editText2)).getText().toString();
        int weight2 = Integer.parseInt(((EditText) findViewById(R.id.editText6)).getText().toString());
        String place3 = ((EditText) findViewById(R.id.editText5)).getText().toString();
        int weight3 = Integer.parseInt(((EditText) findViewById(R.id.editText4)).getText().toString());
        String place4 = ((EditText) findViewById(R.id.editText7)).getText().toString();
        int weight4 = Integer.parseInt(((EditText) findViewById(R.id.editText9)).getText().toString());
        String place5 = ((EditText) findViewById(R.id.editText8)).getText().toString();
        int weight5 = Integer.parseInt(((EditText) findViewById(R.id.editText10)).getText().toString());

        if (findViewById(R.id.rB2).isSelected()){
            weight3 = 0;
            weight4 = 0;
            weight5 = 0;
        }
        else if (findViewById(R.id.rB3).isSelected()){
            weight4 = 0;
            weight5 = 0;
        }
        else if (findViewById(R.id.rB4).isSelected()){
            weight5 = 0;
        }


        Random random = new Random();
        int totalWeight = weight1 + weight2 + weight3 + weight4 + weight5;
        int pick = random.nextInt (totalWeight);

        StringBuffer result = new StringBuffer();

        if (pick <= weight1){result.append(place1);}
        else if (pick <= (weight1 + weight2)){result.append(place2);}
        else if (pick <= (weight1 + weight2 + weight3)){result.append(place3);}
        else if (pick <= (weight1 + weight2 + weight3 + weight4)){result.append(place4);}
        else {result.append(place5);}

        ((TextView) findViewById(R.id.finalChoice)).setText(result);
        result.delete(0, result.length());
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
