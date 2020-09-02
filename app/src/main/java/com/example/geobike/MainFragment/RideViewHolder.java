package com.example.geobike.MainFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.R;

public class RideViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    private ImageView imageView;

    public RideViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(String text){
        textView.setText(text);
    }
}
