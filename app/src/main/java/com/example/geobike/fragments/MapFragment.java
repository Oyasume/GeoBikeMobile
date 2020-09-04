package com.example.geobike.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.geobike.R;
import com.example.geobike.activities.MapViewActivity;

public class MapFragment extends Fragment {

    public static MapFragment newInstance() {
        return (new MapFragment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Button mapView = (Button) view.findViewById(R.id.mapview);
        mapView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(getActivity(), MapViewActivity.class);
                   startActivity(intent);
               }
           }
        );
        return view;
    }
}
