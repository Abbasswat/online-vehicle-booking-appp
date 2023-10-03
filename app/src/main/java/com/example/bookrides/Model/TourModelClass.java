package com.example.bookrides.Model;

import android.widget.Button;
import android.widget.ImageButton;

public class TourModelClass {


    String tripTiltle,tripImage1,tripPrice,tripplanner,tripduration,tripstartplace,tripphone,tripDesc,
            tripDate,tripTime,tripVehicles_types,tripVehicleNum;



    public String getTripDate() {
        return tripDate;
    }

    public String getTripTime() {
        return tripTime;
    }

    public String getTripVehicles_types() {
        return tripVehicles_types;
    }

    public String getTripVehicleNum() {
        return tripVehicleNum;
    }

    ImageButton tripcallbutton,triplocationbutton,tripwhatsappbutton;
     Button tripdetailbutton;
//    private String key;
//
//    public void setKey(String key) {
//        this.key = key;
//    }

    public TourModelClass(String tripTiltle, String tripPrice, String tripphone, String tripstartplace, String tripduration, String tripplanner, String tripDesc,String tripDate, String tripTime,String tripVehicles_types,String tripVehicleNum ,String tripImage1) {

        this.tripTiltle = tripTiltle;
        this.tripPrice = tripPrice;
        this.tripphone = tripphone;
        this.tripstartplace = tripstartplace;
        this.tripduration = tripduration;
        this.tripplanner = tripplanner;
        this.tripDesc = tripDesc;
        this.tripDate = tripDate;
        this.tripTime = tripTime;
        this.tripVehicles_types = tripVehicles_types;
        this.tripVehicleNum = tripVehicleNum;
        this.tripImage1 = tripImage1;
//
//        this.tripcallbutton = tripcallbutton;
//        this.triplocationbutton = triplocationbutton;
//        this.tripwhatsappbutton = tripwhatsappbutton;
//        this.tripdetailbutton = tripdetailbutton;


    }

    public TourModelClass(){
    }


    public String gettripImage1() {
        return tripImage1;
    }
    public String getTripTiltle() {
        return tripTiltle;
    }
    public String getTripPrice() {
        return tripPrice;
    }
    public String getTripplanner() {
        return tripplanner;
    }
    public String getTripduration() {
        return tripduration;
    }
    public String getTripstartplace() {
        return tripstartplace;
    }



    public String getTripphone() {
        return tripphone;
    }
    public String getTripDesc() {
        return tripDesc;
    }

//    public String getKey() {
//        return key;
//    }





}
