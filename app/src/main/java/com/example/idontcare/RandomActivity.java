package com.example.idontcare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.idontcare.data.model.LoggedInUser;
import com.example.idontcare.ui.login.LoginActivity;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    private LoggedInUser primaryUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //primaryUser = getIntent().getStringExtra("user");



    }

    public void goHome(View view) {
        Intent intent = new Intent (RandomActivity.this, MainActivity.class);
        intent.putExtra("user", (Parcelable) primaryUser);
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
