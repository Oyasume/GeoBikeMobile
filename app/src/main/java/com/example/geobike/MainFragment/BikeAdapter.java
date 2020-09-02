package com.example.geobike.MainFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.R;

public class BikeAdapter extends RecyclerView.Adapter<BikeViewHolder> {
        private String[] mDataset;

        public BikeAdapter(String[] mDataset) {
            this.mDataset = mDataset;
        }

        @NonNull
        @Override
        public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bike_card,parent,false);
            return new BikeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BikeViewHolder holder, int position) {
            holder.bind(mDataset[position]);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
}
