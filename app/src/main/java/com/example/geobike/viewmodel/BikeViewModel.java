package com.example.geobike.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.geobike.models.Bike;
import com.example.geobike.repositories.BikeRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class BikeViewModel extends AndroidViewModel {

    private BikeRepository bikeRepository;

    public BikeViewModel(Application application){
        super(application);
        bikeRepository = new BikeRepository(application);
    }

    public Observable<List<Bike>> getAllBikeLiveData(){
        return bikeRepository.getAllBike();
    }

    public Observable<Bike> getOneBikeLiveData(Long id){
        return bikeRepository.getOneBike(id);
    }
}
