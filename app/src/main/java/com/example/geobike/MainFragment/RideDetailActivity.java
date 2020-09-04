package com.example.geobike.MainFragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.geobike.R;

import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

public class RideDetailActivity extends Activity {
    private MapView mapView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_detail_activity);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
    }
}
