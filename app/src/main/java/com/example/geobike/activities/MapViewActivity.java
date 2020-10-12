package com.example.geobike.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.geobike.R;
import com.example.geobike.models.Ride;
import com.example.geobike.viewmodel.RideViewModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapController;

import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapViewActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView mapView = null;
    private MapController mapController;
    private MyLocationNewOverlay mLocationOverlay;
    private ImageButton myLocationButton;
    private Context ctx;
    LocationManager locationManager;
    private OverlayItem overlayItem;
    Geocoder geocoder;
    List<com.example.geobike.models.Location> trajet;
    CompassOverlay mCompassOverlay;
    RotationGestureOverlay mRotationGestureOverlay;
    ScaleBarOverlay myScaleBarOverlay;
    private int etat;
    Polyline line ;
    private String fournisseur;
    private ImageButton recordButton;
    boolean isRecording;
    RideViewModel rideViewModel;

    LocationListener ecouteurGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location localisation) {
//            Toast.makeText(getApplicationContext(), fournisseur + " localisation", Toast.LENGTH_SHORT).show();
            Log.d("GPS", "localisation : " + localisation.toString());

            String coordonnees = String.format("Latitude : %f - Longitude : %f\n", localisation.getLatitude(), localisation.getLongitude());
            Log.d("GPS", coordonnees);

            String autres = String.format("Vitesse : %f - Altitude : %f - Cap : %f\n", localisation.getSpeed(), localisation.getAltitude(), localisation.getBearing());
            Log.d("GPS", autres);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date(localisation.getTime());
            Log.d("GPS", sdf.format(date));

            mapView.getController().setCenter(new GeoPoint(localisation.getLatitude(), localisation.getLongitude()));

//            trajet.add(new GeoPoint(localisation.getLatitude(), localisation.getLongitude()));
//
            // Un tracé à base de lignes rouges
//            Polyline line = new Polyline(mapView);
//            line.setTitle("Un trajet");
//            line.setSubDescription(Polyline.class.getCanonicalName());
//            line.setWidth(10f);
//            line.setColor(Color.RED);
//            line.setPoints(trajet);
//            line.setGeodesic(true);
//            line.setInfoWindow(new BasicInfoWindow(R.layout.bonuspack_bubble, mapView));
//            mapView.getOverlayManager().add(line);
            if(line != null && isRecording == true){
                line.addPoint(new GeoPoint(localisation.getLatitude(), localisation.getLongitude()));
            }
            if(isRecording == true) {
                trajet.add(new com.example.geobike.models.Location("namese", localisation.getLatitude(), localisation.getLongitude(), Instant.now()));
                Log.e("MapViewActivity", "trajet: "+trajet.size());

            }

//
//
//            mapView.invalidate();


//            Address adresse = getAddressFromLocation(localisation);
//            ArrayList<String> addressFragments = new ArrayList<String>();
//            String strAdresse = adresse.getAddressLine(0) + ", " + adresse.getLocality();
//            Log.d("GPS", "adresse : " + strAdresse);
//            for(int i = 0; i <= adresse.getMaxAddressLineIndex(); i++)
//            {
//                addressFragments.add(adresse.getAddressLine(i));
//            }
//            Log.d("GPS", TextUtils.join(System.getProperty("line.separator"), addressFragments));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (etat != status){
                switch (status){
                    case LocationProvider.AVAILABLE:
                        Toast.makeText(getApplicationContext(), fournisseur + " état disponible", Toast.LENGTH_SHORT).show();
                        break;
                    case LocationProvider.OUT_OF_SERVICE:
                        Toast.makeText(getApplicationContext(), fournisseur + " état indisponible", Toast.LENGTH_SHORT).show();
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Toast.makeText(getApplicationContext(), fournisseur + " état temporairement indisponible", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), fournisseur + " état : " + status, Toast.LENGTH_SHORT).show();
                }
            }
            etat = status;
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), fournisseur + " activé !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), fournisseur + " désactivé !", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        Log.d("GPS", "onCreate");

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        geocoder = new Geocoder(this, Locale.getDefault());
        etat = 0;
        trajet = new ArrayList<com.example.geobike.models.Location>();
        isRecording =false;
        rideViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(RideViewModel.class);


        mapView = (MapView) findViewById(R.id.mapview);
//        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.setClickable(true);


        mapController = (MapController) mapView.getController();
        mapController.setZoom(18);// 9.5
//        mapController.animateTo(new GeoPoint(50.936255, 6.957779));
//        mapView.setScrollableAreaLimitDouble(new BoundingBox(85, 180, -85, -180));
//        mapView.setMaxZoomLevel(10.0);
//        mapView.setMinZoomLevel(4.0);
//        mapView.setHorizontalMapRepetitionEnabled(false);
//        mapView.setVerticalMapRepetitionEnabled(false);
//        mapView.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(), MapView.getTileSystem().getMinLatitude(), 0);
//        mapView.getOverlays().add(rotationGestureOverlay);
//        mapView.getOverlays().add(compassOverlay);
//        mapView.getOverlays().add(myLocationNewOverlay);
//        mapView.getOverlays().add(roadNodeMarkers);

        //echelle
        myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);

        //Boussole
        mCompassOverlay = new CompassOverlay(getApplicationContext(), new InternalCompassOrientationProvider(getApplicationContext()), mapView);
        mCompassOverlay.enableCompass();
        mapView.getOverlays().add(mCompassOverlay);


        mRotationGestureOverlay = new RotationGestureOverlay(getApplicationContext(), mapView);
        mRotationGestureOverlay.setEnabled(true);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(this.mRotationGestureOverlay);


//        ctx = getApplicationContext();
//        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
//        GpsMyLocationProvider imlp = new GpsMyLocationProvider(this.getBaseContext());
//        imlp.setLocationUpdateMinDistance(1000);
//        imlp.setLocationUpdateMinTime(60000);

        //initialiser localisation
        if(locationManager == null){
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteres = new Criteria();

            // la précision  : (ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision)
            criteres.setAccuracy(Criteria.ACCURACY_FINE);

            // l'altitude
            criteres.setAltitudeRequired(true);

            // la direction
            criteres.setBearingRequired(true);

            // la vitesse
            criteres.setSpeedRequired(true);

            // la consommation d'énergie demandée
            criteres.setCostAllowed(true);

            //criteres.setPowerRequirement(Criteria.POWER_HIGH);
            criteres.setPowerRequirement(Criteria.POWER_MEDIUM);

            fournisseur = locationManager.getBestProvider(criteres, true);
            Log.d("GPS", "fournisseur : " + fournisseur);

        }

        //fournisseur
        if(fournisseur != null){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Log.d("GPS", "no permissions !");
                return;
            }

            Location location = locationManager.getLastKnownLocation(fournisseur);
            if(location != null){
                // on notifie la localisation
                ecouteurGPS.onLocationChanged(location);
            }

            // on configure la mise à jour automatique : au moins 10 mètres et 1 secondes
            locationManager.requestLocationUpdates(fournisseur, 1000, 10, ecouteurGPS);

        }

        //position tel
        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mapView);
        mLocationOverlay.enableMyLocation();
        mapView.setMultiTouchControls(true);
//        mLocationOverlay.setUseSafeCanvas(false);
//        mLocationOverlay.setDrawAccuracyEnabled(true);
        mapView.getOverlays().add(mLocationOverlay);

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),mapView);
//        mLocationOverlay.enableMyLocation();
//        mapView.getOverlays().add(mLocationOverlay);
//        requestPermissionsIfNecessary(new String[] {
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        });

        //Bouton ma localisation
        myLocationButton = (ImageButton) findViewById(R.id.map_my_location_button) ;
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mapviewActivity", "click my location");
                if(fournisseur != null) {
                    if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("GPS", "no permissions !");
                        return;
                    }

                    Location location = locationManager.getLastKnownLocation(fournisseur);

                    mapView.getController().animateTo(new GeoPoint(location.getLatitude(), location.getLongitude()),20D, 1000L);
                }


            }
        });

        recordButton = (ImageButton) findViewById(R.id.map_record) ;
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mapviewActivity", "record");
                isRecording = !isRecording;
                if(isRecording){
                    recordButton.setBackgroundResource(R.drawable.record_on);
                }else {
                    recordButton.setBackgroundResource(R.drawable.record_off);
                }

                if(!isRecording){
                    new AlertDialog.Builder(MapViewActivity.this)
                    .setTitle("Nouveau trajet")
                    .setMessage("Donnez un nom à votre trajet")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            EditText nom = (EditText) ((AlertDialog) dialog).findViewById(R.id.nom);

//                            rideViewModel.registerRide()
                            Log.e("MapViewActivity", "trajet: "+trajet.size());

                            Set<com.example.geobike.models.Location> listLoc = trajet.stream()
                                .map(location -> location)
                                .collect(Collectors.toSet());

                            Log.e("MapViewActivity", "listloc: "+listLoc.size());

                            Ride rideToSave = new Ride(nom.getText().toString(), listLoc );
                            rideViewModel.registerRide(rideToSave)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<Ride>() {
                                        @Override
                                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull  Ride ride) {
                                            Log.e("MapViewActivity", "Ride registered id: " + ride.getId());
                                            trajet = new ArrayList<>();

                                        }

                                        @Override
                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                            Toast toast = Toast.makeText(getApplicationContext(), "Tajet " + nom.getText() + " enregistré", Toast.LENGTH_SHORT);
                            toast.show();
                            dialog.dismiss();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setView(R.layout.alert_dialog_register)
                    .show();

                }
            }
        });

        line = new Polyline(mapView);
        line.setTitle("Un trajet");
        line.setSubDescription(Polyline.class.getCanonicalName());
        line.setWidth(10f);
        line.setColor(Color.RED);
        line.setGeodesic(true);
        line.setInfoWindow(new BasicInfoWindow(R.layout.bonuspack_bubble, mapView));
        mapView.getOverlayManager().add(line);


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mLocationOverlay.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        mLocationOverlay.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //arreter la localisation
        if(locationManager != null){
            locationManager.removeUpdates(ecouteurGPS);
            ecouteurGPS = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void updateLoc(Location loc){
        GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
        mapController.setCenter(locGeoPoint);

        setOverlayLoc(loc);

        mapView.invalidate();
    }

    private void setOverlayLoc(Location overlayloc){
        GeoPoint overlocGeoPoint = new GeoPoint(overlayloc);

    /*    overlayItemArray.clear();

        OverlayItem newMyLocationItem = new OverlayItem(
                "My Location", "My Location", overlocGeoPoint);
        overlayItemArray.add(newMyLocationItem);*/
        overlayItem = new OverlayItem("location", "location", overlocGeoPoint);

    }

    private Address getAddressFromLocation(Location location){
        List<Address> addresses = null;
        try{
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
        }catch (IOException ioException){
            Log.e("GPS", "erreur", ioException);
        }catch (IllegalArgumentException illegalArgumentException)
        {
            Log.e("GPS", "erreur " +  location.toString(), illegalArgumentException);
        }

        if (addresses == null || addresses.size()  == 0){
            Log.e("GPS", "erreur aucune adresse !");
        }else {
            Log.e("GPS", "addresse : " + addresses.toString());

        }
        return addresses.get(0);
    }
}
