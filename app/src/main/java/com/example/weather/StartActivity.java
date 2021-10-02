package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
                for(int i=0; i<continents.length;i++) {
                    if (city.getText().toString().toUpperCase().equals(continents[i])) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Weather not found!", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    else
                    {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putExtra("City", city.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }
}
