package com.example.geobike.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.geobike.models.Ride;
import com.example.geobike.service.RideService;
import com.example.geobike.utils.RetrofitService;
import com.example.geobike.utils.SessionManager;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RideRepository {

    private RideService rideService;
    private LiveData<Ride> rideLiveData;
    private LiveData<List<Ride>> rideListLiveData;

    public RideRepository(Application application) {
        Retrofit retrofitService = RetrofitService.getInstance(new SessionManager(application));
        this.rideService = retrofitService.create(RideService.class);
    }

    public Observable<Ride> getOneRide(Long id){
        return rideService.getOneRide(id);
    }

    public Observable<List<Ride>> getAllRide(){
        return rideService.getAllRide();
    }

    public LiveData<Ride> getOneRide2(Long id){
//        MutableLiveData<Ride> rideLiveData = new MutableLiveData<>();
//        rideService.getOneRide(id).enqueue(new Callback<Ride>() {
//            @Override
//            public void onResponse(Call<Ride> call, Response<Ride> response) {
//                if( response.isSuccessful()){
//                    rideLiveData.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Ride> call, Throwable t) {
//                rideLiveData.setValue(null);
//            }
//        });
//        return rideLiveData;
        return null;
    }

    public LiveData<List<Ride>> getAllRide2(){
//        MutableLiveData<List<Ride>> rideListLiveData = new MutableLiveData<>();
//        rideService.getAllRide().enqueue(new Callback<List<Ride>>() {
//            @Override
//            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
//                if(response.isSuccessful()){
//                    rideListLiveData.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Ride>> call, Throwable t) {
//                rideListLiveData.setValue(null);
//            }
//        });
//        return rideListLiveData;
        return null;
    }

}
