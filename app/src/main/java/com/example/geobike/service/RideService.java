package com.example.geobike.service;

import com.example.geobike.models.Ride;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RideService {

    @GET("travels/{id}")
    Observable<Ride> getOneRide(@Path("id") Long id);

    @GET("travels/")
    Observable<List<Ride>> getAllRide();
}
