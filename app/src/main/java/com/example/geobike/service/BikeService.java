package com.example.geobike.service;

import com.example.geobike.models.Bike;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BikeService{

    @GET("bikes/{id}")
    Observable<Bike> getOneBike(@Path("id") Long id);

    @GET("bikes/")
    Observable<List<Bike>> getAllBikes();
}
