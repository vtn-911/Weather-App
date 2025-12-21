package com.example.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.module.ForecastRespone;
import com.example.weatherapp.module.WeatherRespone;
import com.example.weatherapp.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {
    private WeatherRepository repository = new WeatherRepository();
    private MutableLiveData<WeatherRespone> weatherLiveData =new MutableLiveData<>();
    private MutableLiveData<ForecastRespone> forecastLiveData = new MutableLiveData<>();
    public LiveData<WeatherRespone> getWeatherLiveData(){
        return weatherLiveData;
    }
    public void loadWeather(double lon, double lat){
        repository.getWeather(lon,lat,weatherLiveData);
    }
    public  LiveData<ForecastRespone> getForecastLiveData(){
        return forecastLiveData;
    }
    public void loadForecast(double lon, double lat){
        repository.getForeCast(lon,lat,forecastLiveData);
    }
}
