package com.example.geobike.viewholder;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.geobike.activities.BikeDetailActivity;
import com.example.geobike.R;
import com.example.geobike.models.Bike;

public class BikeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder{

    public Bike bike;
    public TextView textView;
    public ImageView imageView;

    public BikeViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(Bike bike){
        textView.setText(bike.getName());
        this.bike = bike;
        if(bike.getImage() != null){
            byte[] bytes = Base64.decode(bike.getImage(), Base64.DEFAULT);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }

//        Bitmap bitmap = BitmapFactory.decodeByteArray(bike.getImage(), 0, bike.getImage().length);
//        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        animateIntent(v);
    }

    public void animateIntent(View v) {
        Intent intent = new Intent(v.getContext(), BikeDetailActivity.class);
        String transitionName = v.getContext().getString(R.string.transition_string);
        View viewStart = v.findViewById(R.id.bikeCardView);
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),
                        viewStart,
                        transitionName
                );
        Bundle bundle = new Bundle();
        Log.e("bike view holder", this.bike.toString());
        bundle.putLong("id", bike.getId());
        intent.putExtras(bundle);

        v.getContext().startActivity(intent, options.toBundle());
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}
