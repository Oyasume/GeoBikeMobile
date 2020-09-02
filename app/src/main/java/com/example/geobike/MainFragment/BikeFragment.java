package com.example.geobike.MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.R;

public class BikeFragment extends Fragment {

    String[] mDataset = {"Bike 1", "Bike 2", "Bike 3", "Bike 4", "Bike 5", "Bike 6", "Bike 7", "Bike 8", "Bike 9", "Bike 10" };

    public static BikeFragment newInstance() {
        return (new BikeFragment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bike_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new BikeAdapter(mDataset);
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
