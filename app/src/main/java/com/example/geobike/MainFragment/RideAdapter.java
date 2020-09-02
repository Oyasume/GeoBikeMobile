package com.example.geobike.MainFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.R;


public class RideAdapter extends RecyclerView.Adapter<RideViewHolder> {
    private String[] mDataset;

    public RideAdapter(String[] mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_card,parent,false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        holder.bind(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
