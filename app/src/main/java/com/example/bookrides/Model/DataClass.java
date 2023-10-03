package com.example.bookrides.Model;

import com.google.firebase.database.Exclude;

import java.util.UUID;

public class DataClass {
    private String dataTitle;
    private String dataDesc;
    private String dataTime;
    private String dataImage;
    private String dataPhone;
    private String dataAddress;
    public String dataDate;
    public String dataDestination;
    public String dataCompanyName;
    public String dataBus_types;
    private String key; // Add this field

    // Other fields and constructors...

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getDataDestination() {
        return dataDestination;
    }

    public String getDataCompanyName() {
        return dataCompanyName;
    }

    public String getdataBus_types() {
        return dataBus_types;
    }

    public String getDataDate() {
        return dataDate;
    }


    public String getDataTitle() {
        return dataTitle;
    }
    public String getDataDesc() {
        return dataDesc;
    }
    public String getDataTime() {
        return dataTime;
    }
    public String getDataPhone() {
        return dataPhone;
    }
    public String getDataAddress() {
        return dataAddress;
    }
    public String getDataImage() {
        return dataImage;
    }
    public DataClass(String dataTitle, String dataDesc, String dataLang, String dataPhone,String dataAddress,String dataDate,String dataDestination, String dataCompanyName,String databus_types, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataTime = dataLang;
        this.dataImage = dataImage;
        this.dataPhone = dataPhone;
        this.dataAddress = dataAddress;
        this.dataDate = dataDate;
        this.dataDestination = dataDestination;
        this.dataCompanyName = dataCompanyName;
        this.dataBus_types = databus_types;


    }
    public DataClass(){
    }
}