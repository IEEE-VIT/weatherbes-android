package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {


    EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        city=findViewById(R.id.city_name);

        final String continents[] = {"ASIA","EUROPE", "AFRICA","AUSTRALIA","NORTH AMERICA","SOUTH AMERICA","ANTARCTICA"};
        Button go=findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isContinent = false;
                for (int i = 0; i < continents.length; i++) {
                    if (city.getText().toString().toUpperCase().equals(continents[i])) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Weather not found!", Toast.LENGTH_LONG);
                        toast.show();
                        isContinent = true;
                        break;
                    }
                }

                if(!isContinent) {
                    String inputCity = city.getText().toString().trim();
                    if (!inputCity.equals("")) {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putExtra("City", inputCity);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(StartActivity.this, "Please enter a city's name!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
