package com.example.weatherapp.module;

import java.util.List;

public class ForecastRespone {
    private List<ListForecast> list;

    public List<ListForecast> getList() {
        return list;
    }

    public void setList(List<ListForecast> list) {
        this.list = list;
    }
}
