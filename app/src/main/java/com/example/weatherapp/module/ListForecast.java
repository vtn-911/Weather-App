package com.example.weatherapp.module;

import java.util.List;

public class ListForecast {
    private long dt;
    private MainForecast main;
    private List<WeatherForecast> weather;
    private String dt_txt;

    public MainForecast getMain() {
        return main;
    }

    public void setMain(MainForecast main) {
        this.main = main;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public List<WeatherForecast> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherForecast> weather) {
        this.weather = weather;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
