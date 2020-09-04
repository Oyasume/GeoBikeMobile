package com.example.geobike.MainFragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.R;

import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

public class RideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    private MapView mapView;

    public RideViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.text);
        mapView = (MapView) itemView.findViewById(R.id.mapview);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);

    }

    public void bind(String text){
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        animateIntent(v);
    }

    public void animateIntent(View v) {
        Intent intent = new Intent(v.getContext(), RideDetailActivity.class);
        String transitionName = v.getContext().getString(R.string.transition_string);
        View viewStart = v.findViewById(R.id.rideCardView);
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),
                        viewStart,
                        transitionName
                );
        v.getContext().startActivity(intent, options.toBundle());
    }
}
