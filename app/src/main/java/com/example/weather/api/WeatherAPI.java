package com.example.weather.api;

import com.example.weather.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {


    @GET("weather")
    Call<Weather> getWeather(@Query("q") String city_name,
                             @Query("APPID") String api_key);
}
