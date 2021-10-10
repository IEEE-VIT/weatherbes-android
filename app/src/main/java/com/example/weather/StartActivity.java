package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.api.WeatherAPI;
import com.example.weather.model.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivity extends AppCompatActivity implements TextWatcher {


    AutoCompleteTextView city;
    ArrayList<String> cities;

    void isValidCity(final String city)
    {

        String key = "68e0849e2278e59e44e67ee712a368e0";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final WeatherAPI weatherApi = retrofit.create(WeatherAPI.class);
        Call<Weather> call = weatherApi.getWeather(city, key);

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if(response.body()!=null) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("City", city);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(StartActivity.this, "Please enter a valid city name", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        city=findViewById(R.id.city_name);

        //connecting citties arrayList with worldcities.csv(list of city names)
        cities=new ArrayList<>();
        String path = "worldcities.csv";
        String line = "";
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(getAssets()
                    .open("worldcities.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                cities.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        city.addTextChangedListener(this);
        city.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities));



        final String continents[] = {"ASIA","EUROPE", "AFRICA","AUSTRALIA","NORTH AMERICA","SOUTH AMERICA","ANTARCTICA"};
        Button go=findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            private long mLastClickTime = 0;
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                for (int i = 0; i < continents.length; i++) {
                    if (city.getText().toString().toUpperCase().equals(continents[i])) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Weather not found!", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    else {
                        String inputCity = city.getText().toString().trim();
                        if (!inputCity.equals("")) {

                            isValidCity(inputCity);

                            break;

                        } else {

                            Toast.makeText(StartActivity.this, "Please enter a city's name!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}


