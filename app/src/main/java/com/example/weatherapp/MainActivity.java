package com.example.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import com.example.weatherapp.api.ApiClient;
import com.example.weatherapp.module.WeatherRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView txtnameCity, txttemp, txtmainWeather;
    ImageView imgWeather;
    FusedLocationProviderClient fusedLocationProviderClient;
    ActivityResultLauncher<String> permissionLauncher;

    private String apiKey = "ecfad4ce34b0a4dba5fa9f98f5ab2694";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtnameCity = findViewById(R.id.nameCity);
        txttemp = findViewById(R.id.temp);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted){
                getRealLocation();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            getRealLocation();
        }else{
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }


    }

    private void getRealLocation() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null){
                        double lon = location.getLongitude();
                        double lat = location.getLatitude();
                        callWeatherAPI(lon,lat);
                    }else {
                        resquestNewLocation();
                    }
                });
    }
    private void resquestNewLocation(){
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        LocationRequest request = LocationRequest.create()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setInterval(3000)
                .setFastestInterval(1000)
                .setNumUpdates(1);
        fusedLocationProviderClient.requestLocationUpdates(request, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                // Gọi API tại đây nếu lấy location mới
                callWeatherAPI(location.getLongitude(),location.getLatitude());
                fusedLocationProviderClient.removeLocationUpdates(this);
            }
        }, Looper.getMainLooper());
    }
    private void callWeatherAPI(double lon, double lat){
        ApiClient.apiSV.getWeatherRespone(lon,lat,apiKey,"metric","vi")
                .enqueue(new Callback<WeatherRespone>() {
                    @Override
                    public void onResponse(Call<WeatherRespone> call, Response<WeatherRespone> response) {
                        WeatherRespone data = response.body();
                        txtnameCity.setText(data.getName());
                        int temp = (int) data.getMain().getTemp();
                        txttemp.setText(String.valueOf(temp) + "°C");
                    }

                    @Override
                    public void onFailure(Call<WeatherRespone> call, Throwable t) {

                    }
                });
    }
}
