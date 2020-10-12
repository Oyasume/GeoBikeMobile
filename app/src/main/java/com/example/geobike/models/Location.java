package com.example.geobike.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class Location {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("date")
//    @Expose(serialize = false)
    private String date;

//    @SerializedName("date")
//    @Expose(deserialize = false)
    private Instant dateInstant;

//    private Ride ride;


    public Location(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String name, Double latitude, Double longitude, Instant dateInstant) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateInstant = dateInstant;
        this.date = dateInstant.toString();
        Log.e("Location", dateInstant.toString());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Instant getDateInstant() {
        if(this.dateInstant == null){
            return Instant.parse(this.date);
//            return LocalDateTime.parse(this.date, DateTimeFormatter.ISO_INSTANT)
//                    .atZone(TimeZone.getDefault().toZoneId())
//                    .toInstant();
        }else{
            return dateInstant;
        }
    }

    public void setDateInstant(Instant dateInstant) {
        this.dateInstant = dateInstant;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
