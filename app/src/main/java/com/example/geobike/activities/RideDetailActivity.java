package com.example.geobike.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.geobike.R;
import com.example.geobike.models.Location;
import com.example.geobike.models.Ride;
import com.example.geobike.viewmodel.RideViewModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RideDetailActivity extends AppCompatActivity {
    private TextView textView;
    private MapView mapView = null;
    private RideViewModel rideViewModel;
    private Ride ride;
    CompassOverlay mCompassOverlay;
    ScaleBarOverlay myScaleBarOverlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        //Congigure user agent pour osm
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        textView = (TextView) findViewById(R.id.bikename);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);

        //echelle
        myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);

        //Boussole
        mCompassOverlay = new CompassOverlay(getApplicationContext(), new InternalCompassOrientationProvider(getApplicationContext()), mapView);
        mCompassOverlay.enableCompass();
        mapView.getOverlays().add(mCompassOverlay);

        Bundle bundle = getIntent().getExtras();
        Long value = -1L; // or other values
        if(bundle != null)
            value = bundle.getLong("id");

        rideViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(RideViewModel.class);
        rideViewModel.getOneBikeLiveData(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(ride1 -> {
                    textView.setText(ride1.getName());
                    ride = ride1;
                    try{
                        Log.e("RideDetailActivity", "Traçage ligne");
                        List<GeoPoint> trajet = ride1.getLocations().stream()
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
                        Log.e("RideViewHolder", "maxLat: " + maxLat);
                        Log.e("RideViewHolder", "maxLong: " + maxLong);
                        Log.e("RideViewHolder", "minLat: " + minLat);
                        Log.e("RideViewHolder", "minLong: " + minLong);

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
                        mapView.zoomToBoundingBox(boundingBox.increaseByScale(1.3f),false);

                    }catch (NullPointerException e){
                        Log.e("RideDetailActivity", "NullPointerException");
                    }
                })
                .subscribe();
    }



}
