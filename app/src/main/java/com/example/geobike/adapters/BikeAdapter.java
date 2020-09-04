package com.example.geobike.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.viewholder.BikeViewHolder;
import com.example.geobike.others.OnStartDragListener;
import com.example.geobike.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeViewHolder> implements ItemTouchHelperAdapter {
    private List<String> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;

    public BikeAdapter(List<String> mItems, OnStartDragListener dragStartListener) {
        this.mItems = mItems;
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
        holder.bind(mItems.get(position));
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
        return mItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }
}
