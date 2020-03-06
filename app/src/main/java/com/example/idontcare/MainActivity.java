package com.example.idontcare;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}
