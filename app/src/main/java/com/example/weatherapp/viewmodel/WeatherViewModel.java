package com.example.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.module.WeatherRespone;
import com.example.weatherapp.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {
    private WeatherRepository repository = new WeatherRepository();
    private MutableLiveData<WeatherRespone> weatherLiveData =new MutableLiveData<>();
    public LiveData<WeatherRespone> getWeather(double lon, double lat){
        weatherLiveData = (MutableLiveData<WeatherRespone>) repository.getWeather(lon,lat);
        return weatherLiveData;
    }
}
