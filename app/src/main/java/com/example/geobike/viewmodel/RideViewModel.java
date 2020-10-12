package com.example.geobike.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.geobike.models.Ride;
import com.example.geobike.repositories.RideRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class RideViewModel extends AndroidViewModel {

    private RideRepository rideRepository;

    public RideViewModel(@NonNull Application application) {
        super(application);
        rideRepository = new RideRepository(application);
    }

    public Observable<List<Ride>> getAllRideLiveData(){
        return rideRepository.getAllRide();
    }

    public Observable<Ride> getOneBikeLiveData(Long id){
        return rideRepository.getOneRide(id);
    }
}
