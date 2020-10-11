package com.example.geobike.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.models.Bike;
import com.example.geobike.viewholder.BikeViewHolder;
import com.example.geobike.others.OnStartDragListener;
import com.example.geobike.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeViewHolder> implements ItemTouchHelperAdapter {
    private List<Bike> bikeList;
    private final OnStartDragListener mDragStartListener;

    public BikeAdapter(OnStartDragListener dragStartListener) {
        this.bikeList = new ArrayList<>();
        mDragStartListener = dragStartListener;
    }

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_bike,parent,false);
        return new BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BikeViewHolder holder, int position) {
        holder.bind(bikeList.get(position));
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                mDragStartListener.onStartDrag(holder);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("bike adapter: ", bikeList.size()+"");
        return bikeList.size();
    }

    public void setBikes(List<Bike> bikes){
        this.bikeList = bikes;
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(bikeList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        bikeList.remove(position);
        notifyItemRemoved(position);
    }

}
