package com.example.bookrides.Model;

import android.widget.Button;
import android.widget.ImageButton;

public class LoaderModelClass {


    String loaderUsername,loaderEmail, loaderImage,loaderlocation,loaderphone,loadertVehicle,vehiclenumber;




    public LoaderModelClass(String loaderUsername, String loaderEmail, String loaderphone, String loaderlocation, String loadertVehicle,String vehiclenumber, String loaderImage) {

        this.loaderUsername = loaderUsername;
        this.loaderEmail = loaderEmail;
        this.loaderphone = loaderphone;
        this.loaderlocation = loaderlocation;
        this.loadertVehicle = loadertVehicle;
        this.vehiclenumber = vehiclenumber;
        this.loaderImage = loaderImage;
    }

    public LoaderModelClass(){
    }


    public void setLoaderUsername(String loaderUsername) {
        this.loaderUsername = loaderUsername;
    }

    public void setLoaderEmail(String loaderEmail) {
        this.loaderEmail = loaderEmail;
    }

    public void setLoaderImage(String loaderImage) {
        this.loaderImage = loaderImage;
    }

    public void setLoaderlocation(String loaderlocation) {
        this.loaderlocation = loaderlocation;
    }

    public void setLoaderphone(String loaderphone) {
        this.loaderphone = loaderphone;
    }

    public void setLoadertVehicle(String loadertVehicle) {
        this.loadertVehicle = loadertVehicle;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public String getLoaderUsername() {
        return loaderUsername;
    }

    public String getLoaderEmail() {
        return loaderEmail;
    }

    public String getLoaderImage() {
        return loaderImage;
    }

    public String getLoaderlocation() {
        return loaderlocation;
    }

    public String getLoaderphone() {
        return loaderphone;
    }

    public String getLoadertVehicle() {
        return loadertVehicle;
    }







}
