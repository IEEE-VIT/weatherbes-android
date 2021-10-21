package com.example.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetrofit {

    public static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
