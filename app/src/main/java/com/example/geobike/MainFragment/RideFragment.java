package com.example.geobike.MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.R;

public class RideFragment extends Fragment {

    String[] mDataset = {"Ride 1", "Ride 2", "Ride 3", "Ride 4", "Ride 5", "Ride 6", "Ride 7", "Ride 8", "Ride 9", "Ride 10" };

    public static RideFragment newInstance() {
        return (new RideFragment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ride_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new RideAdapter(mDataset);
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
