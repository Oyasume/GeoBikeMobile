package com.example.geobike.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.geobike.R;
import com.example.geobike.models.Bike;
import com.example.geobike.utils.GlideApp2;
import com.example.geobike.viewmodel.BikeViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BikeDetailActivity extends AppCompatActivity {

    public TextView textView;
    private ImageView imageView;
    private BikeViewModel bikeViewModel;
    private Bike bike;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_detail);

        textView = (TextView) findViewById(R.id.bikename);
        imageView = (ImageView) findViewById(R.id.bikeimage);

        Bundle bundle = getIntent().getExtras();
        Long value = -1L; // or other values
        if(bundle != null)
            value = bundle.getLong("id");

        bikeViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(BikeViewModel.class);
//        bikeViewModel.getOneBikeLiveData(value).observe(this, new Observer<Bike>() {
//            @Override
//            public void onChanged(Bike bike) {
//                textView = (TextView) findViewById(R.id.bikename);
//                textView.setText(bike.getName());
//            }
//        });
        bikeViewModel.getOneBikeLiveData(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e("BikeDetailActivity", throwable.getMessage()))
                .doOnNext(bike1 ->  {
                    textView.setText(bike1.getName());
                    try{
                        Log.e("BikeDetailActivity", bike1.getImage());
                        imageView.setImageBitmap(bike1.getImageBitmap());
//                        Glide.with(this)
//                                .asBitmap()
//                                .load(bike1.getImage())
//                                .error(R.drawable.bike)
//                                .placeholder(R.drawable.bike)
//                                .into(imageView);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bike1.getImage().getBytes(), 0, bike1.getImage().length());
//                        imageView.setImageBitmap(bitmap);
                    }catch (NullPointerException e){
                        Log.e("BikeDetailActivity", "" + e);
                    }

//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bike.getImage(), 0, bike.getImage().length);
//                    imageView.setImageBitmap(bitmap);
                })
                .subscribe();






    }

}
