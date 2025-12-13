package com.example.weatherapp.api;

import com.example.weatherapp.module.ForecastRespone;
import com.example.weatherapp.module.WeatherRespone;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather")
    Call<WeatherRespone> getWeatherRespone(
            @Query("lon") double lon,
            @Query("lat") double lat,
            @Query("appid") String apiKey,
            @Query("units") String unit,
            @Query("lang") String lang
    );
    @GET("forecast")
    Call<ForecastRespone> getForecastRespone(
            @Query("lon") double lon,
            @Query("lat") double lat,
            @Query("appid") String apiKey,
            @Query("units") String unit,
            @Query("lang") String lang
    );
}
