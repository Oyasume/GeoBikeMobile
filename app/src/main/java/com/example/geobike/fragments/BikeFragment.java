package com.example.geobike.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.adapters.BikeAdapter;
import com.example.geobike.models.Bike;
import com.example.geobike.others.OnStartDragListener;
import com.example.geobike.others.SimpleItemTouchHelperCallback;
import com.example.geobike.R;
import com.example.geobike.viewmodel.BikeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BikeFragment extends Fragment implements OnStartDragListener {

    private BikeViewModel bikeViewModel;
    private ItemTouchHelper mItemTouchHelper;
    public BikeAdapter bikeAdapter;

    public static BikeFragment newInstance() {
        return (new BikeFragment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bike, container, false);

        bikeAdapter = new BikeAdapter(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bikeAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(bikeAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        bikeViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(BikeViewModel.class);

//        bikeAdapter.setBikeList(bikeViewModel.getAllBikeLiveData().getValue());

//        ArrayList<Bike> mm = new ArrayList<>();
//        mm.add(bikeViewModel.getOneBikeLiveData(1L).getValue());
//        bikeAdapter.setBikeList(mm);
//


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            bikeViewModel.getAllBikeLiveData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Bike>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Bike> bikes) {
                        Log.d("BikeFragment", " onNext");
                        bikeAdapter.setBikes(bikes);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("BikeFragment", " onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("BikeFragment", " onComplete");
                    }
                });
//        bikeViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(BikeViewModel.class);
////        bikeViewModel.getAllBikeLiveData().observe(getViewLifecycleOwner(), bikes -> bikeAdapter.setBikeList(bikes));
//
//        bikeViewModel.getAllBikeLiveData().observe(getViewLifecycleOwner(), new Observer<List<Bike>>() {
//            @Override
//            public void onChanged(List<Bike> bikes) {
//                Log.e("bike fragment", bikes.size()+"");
//                bikeAdapter.setBikeList(bikes);            }
//        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
