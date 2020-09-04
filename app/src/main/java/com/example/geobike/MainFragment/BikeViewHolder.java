package com.example.geobike.MainFragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.MainActivity;
import com.example.geobike.R;

public class BikeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textView;
    private ImageView imageView;

    public BikeViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(String text){
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        animateIntent(v);
    }

    public void animateIntent(View v) {
        Intent intent = new Intent(v.getContext(), BikeDetailActivity.class);
        String transitionName = v.getContext().getString(R.string.transition_string);
        View viewStart = v.findViewById(R.id.bikeCardView);
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),
                        viewStart,
                        transitionName
                );
        v.getContext().startActivity(intent, options.toBundle());
    }
}
