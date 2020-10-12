package com.example.geobike.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.models.Ride;
import com.example.geobike.viewholder.RideViewHolder;
import com.example.geobike.R;

import java.util.ArrayList;
import java.util.List;


public class RideAdapter extends RecyclerView.Adapter<RideViewHolder> {
    private List<Ride> rideList;

    public RideAdapter() {
        this.rideList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ride,parent,false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        holder.bind(rideList.get(position));
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public void setRides(List<Ride> rides) {
        this.rideList = rides;
        notifyDataSetChanged();
    }
}
