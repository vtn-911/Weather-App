package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.module.ListForecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private List<ListForecast> list = new ArrayList<>();
    public void setData (List<ListForecast> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycleview, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        ListForecast listForecast = list.get(position);
        String[] dt_txt = listForecast.getDt_txt().split(" ");
        String[] time = dt_txt[1].split(":");
        String hourly = time[0];
        holder.hourlyFC.setText(hourly + " giờ");
        holder.tempFC.setText((int) listForecast.getMain().getTemp()+"°");

        String icon = listForecast.getWeather().get(0).getIcon();
        Glide.with(holder.itemView.getContext())
                .load("https://openweathermap.org/img/wn/"+icon+"@4x.png")
                .into(holder.imgFC);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourlyFC, tempFC;
        ImageView imgFC;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hourlyFC = itemView.findViewById(R.id.txtHourlyForecast);
            tempFC = itemView.findViewById(R.id.tempForecast);
            imgFC = itemView.findViewById(R.id.imgForecast);
        }
    }
}
