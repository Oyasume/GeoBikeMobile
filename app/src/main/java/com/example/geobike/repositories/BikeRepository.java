package com.example.geobike.repositories;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.geobike.models.Bike;
import com.example.geobike.service.BikeService;
import com.example.geobike.utils.RetrofitService;
import com.example.geobike.utils.SessionManager;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BikeRepository {

    private BikeService bikeService;
    private LiveData<Bike> bikeLD;
    private LiveData<List<Bike>> listBikeLD;

    public BikeRepository(Application application) {
        Retrofit retrofitService = RetrofitService.getInstance(new SessionManager(application));
        this.bikeService = retrofitService.create(BikeService.class);
    }

    public LiveData<Bike> getOneBike2(Long id){
//        MutableLiveData<Bike> bikeData = new MutableLiveData<>();
//        bikeService.getOneBike(id).enqueue(new Callback<Bike>() {
//            @Override
//            public void onResponse(Call<Bike> call, Response<Bike> response) {
//                if( response.isSuccessful()){
//                    bikeData.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Bike> call, Throwable t) {
//                bikeData.setValue(null);
//            }
//        });
//        return bikeData;
        return null;
    }

    public Observable<Bike> getOneBike(Long id){
        return bikeService.getOneBike(id)
            .doOnNext(bike -> {
                byte[] bytes = Base64.decode(bike.getImage(), Base64.DEFAULT);
                bike.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        });
    }

    public Observable<List<Bike>> getAllBike(){
         return bikeService.getAllBikes();
    }

    public LiveData<List<Bike>> getAllBike2(){
//        MutableLiveData<List<Bike>> bikeListData = new MutableLiveData<>();
//        bikeService.getAllBikes().enqueue(new Callback<List<Bike>>() {
//            @Override
//            public void onResponse(Call<List<Bike>> call, Response<List<Bike>> response) {
//                if(response.isSuccessful()){
//                    bikeListData.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Bike>> call, Throwable t) {
//                bikeListData.setValue(null);
//            }
//        });
//        return bikeListData;
        return null;
    }


}
