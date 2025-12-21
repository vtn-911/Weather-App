package com.example.weatherapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.api.ApiClient;
import com.example.weatherapp.module.ForecastRespone;
import com.example.weatherapp.module.WeatherRespone;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private String apiKey = "ecfad4ce34b0a4dba5fa9f98f5ab2694";

    public void getWeather(double lon, double lat, MutableLiveData<WeatherRespone> liveData){
        ApiClient.apiSV
                .getWeatherRespone(lon,lat,apiKey,"metric","vi")
                .enqueue(new Callback<WeatherRespone>() {
                    @Override
                    public void onResponse(Call<WeatherRespone> call, Response<WeatherRespone> response) {
                        liveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<WeatherRespone> call, Throwable t) {
                        liveData.setValue(null);
                    }
                });
    }
    public void getForeCast(double lon, double lat, MutableLiveData<ForecastRespone> liveData){
        ApiClient.apiSV
                .getForecastRespone(lon,lat,apiKey,"metric","vi")
                .enqueue(new Callback<ForecastRespone>() {
                    @Override
                    public void onResponse(Call<ForecastRespone> call, Response<ForecastRespone> response) {
                        liveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ForecastRespone> call, Throwable t) {
                        liveData.setValue(null);
                    }
                });
    }
}
