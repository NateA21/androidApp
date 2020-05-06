package com.example.idontcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.idontcare.data.SettingsFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button iDontCareButton = findViewById(R.id.iDontCareButton);
        setSupportActionBar(toolbar);

        iDontCareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsActivity = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapsActivity);
            }
        });
    }

    public void pickRandom(View view) {
        String place1 = ((EditText) findViewById(R.id.editText)).getText().toString();
        int weight1 = Integer.parseInt(((EditText) findViewById(R.id.editText3)).getText().toString());
        String place2 = ((EditText) findViewById(R.id.editText2)).getText().toString();
        int weight2 = Integer.parseInt(((EditText) findViewById(R.id.editText6)).getText().toString());
        String place3 = ((EditText) findViewById(R.id.editText5)).getText().toString();
        int weight3 = Integer.parseInt(((EditText) findViewById(R.id.editText4)).getText().toString());

        Random random = new Random();
        int totalWeight = weight1 + weight2 + weight3;
        int pick = random.nextInt (totalWeight);

        StringBuffer result = new StringBuffer();

        if (pick <= weight1){result.append(place1);}
        else if (pick <= (weight1 + weight2)){result.append(place2);}
        else {result.append(place3);}

        ((TextView) findViewById(R.id.finalChoice)).setText(result);


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
