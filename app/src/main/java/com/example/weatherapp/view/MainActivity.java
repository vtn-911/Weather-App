package com.example.weatherapp.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.viewmodel.WeatherViewModel;
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
    WeatherViewModel weatherViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        txtnameCity = findViewById(R.id.nameCity);
        txttemp = findViewById(R.id.temp);
        txtmainWeather = findViewById(R.id.mainWeather);
//        imgWeather = findViewById(R.id.imgWeather);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
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
                        observeWeather(location.getLongitude(),location.getLatitude());
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

                observeWeather(location.getLongitude(),location.getLatitude());

                fusedLocationProviderClient.removeLocationUpdates(this);
            }
        }, Looper.getMainLooper());
    }
    private void observeWeather(double lon, double lat){
        weatherViewModel.getWeather(lon,lat).observe(MainActivity.this,data ->{
            if (data != null){
                txtnameCity.setText(data.getName());
                int temp = (int) data.getMain().getTemp();
                txttemp.setText(String.valueOf(temp) + "°");
                txtmainWeather.setText(data.getWeather().get(0).getMain());
                // Icon xấu quá tạm ẩn ( :v )
//                Glide.with(MainActivity.this)
//                        .load("https://openweathermap.org/img/wn/"+ data.getWeather().get(0).getIcon()+"@4x.png")
//                        .into(imgWeather);
            }
        });
    }

}
