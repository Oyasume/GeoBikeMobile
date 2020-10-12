package com.example.geobike.viewholder;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.R;
import com.example.geobike.activities.RideDetailActivity;
import com.example.geobike.models.Ride;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.util.List;
import java.util.stream.Collectors;

public class RideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    public MapView mapView;
    CompassOverlay mCompassOverlay;
    ScaleBarOverlay myScaleBarOverlay;
    public Ride ride;

    public RideViewHolder(View itemView) {
        super(itemView);

        //Congigure user agent pour osm
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.text);
        mapView = (MapView) itemView.findViewById(R.id.mapview);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.setClickable(false);

        //echelle
        myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);

        //Boussole
        mCompassOverlay = new CompassOverlay(itemView.getContext(), new InternalCompassOrientationProvider(itemView.getContext()), mapView);
        mCompassOverlay.enableCompass();
        mapView.getOverlays().add(mCompassOverlay);
    }



    public void bind(Ride ride){
        this.ride = ride;
        textView.setText(ride.getName());

        List<GeoPoint> trajet = ride.getLocations().stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .map(location -> new GeoPoint(location.getLongitude(), location.getLatitude()))
                .collect(Collectors.toList());

        Double maxLat = trajet.stream()
                .mapToDouble(value1 -> value1.getLatitude())
                .max().getAsDouble();

        Double maxLong = trajet.stream()
                .mapToDouble(value1 -> value1.getLongitude())
                .max().getAsDouble();

        Double minLat = trajet.stream()
                .mapToDouble(value1 -> value1.getLatitude())
                .min().getAsDouble();

        Double minLong = trajet.stream()
                .mapToDouble(value1 -> value1.getLongitude())
                .min().getAsDouble();

        Polyline line = new Polyline(mapView);
        line.setTitle("Un trajet");
        line.setSubDescription(Polyline.class.getCanonicalName());
        line.setWidth(10f);
        line.setColor(Color.RED);
        line.setPoints(trajet);
        line.setGeodesic(true);
        line.setInfoWindow(new BasicInfoWindow(R.layout.bonuspack_bubble, mapView));

        mapView.getOverlayManager().add(line);

        BoundingBox boundingBox = new BoundingBox(maxLat, maxLong, minLat, minLong);
        mapView.post(new Runnable() {
            @Override
            public void run() {
                mapView.zoomToBoundingBox(boundingBox.increaseByScale(1.3f),false);
            }
        });
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
        Bundle bundle = new Bundle();
        Log.e("ride view holder", this.ride.getName());
        bundle.putLong("id", ride.getId());
        intent.putExtras(bundle);

        v.getContext().startActivity(intent, options.toBundle());


    }
}
