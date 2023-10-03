package com.example.bookrides.Model;

public class SchoolModelClass {

    String sch_name,sch_email,sch_phone,sch_location,sch_vehicleType,sch_studentType,sch_vehiclenumber,sch_time,sch_collegename,sch_image,sch_desc;

    public String getSch_desc() {
        return sch_desc;
    }

    public void setSch_desc(String sch_desc) {
        this.sch_desc = sch_desc;
    }

//    private String key;
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }

    public String getSch_name() {
        return sch_name;
    }

    public void setSch_name(String sch_name) {
        this.sch_name = sch_name;
    }

    public String getSch_email() {
        return sch_email;
    }

    public void setSch_email(String sch_email) {
        this.sch_email = sch_email;
    }

    public String getSch_phone() {
        return sch_phone;
    }

    public void setSch_phone(String sch_phone) {
        this.sch_phone = sch_phone;
    }

    public String getSch_location() {
        return sch_location;
    }

    public void setSch_location(String sch_location) {
        this.sch_location = sch_location;
    }

    public String getSch_vehicleType() {
        return sch_vehicleType;
    }

    public void setSch_vehicleType(String sch_vehicleType) {
        this.sch_vehicleType = sch_vehicleType;
    }

    public String getSch_studentType() {
        return sch_studentType;
    }

    public void setSch_studentType(String sch_studentType) {
        this.sch_studentType = sch_studentType;
    }

    public String getSch_vehiclenumber() {
        return sch_vehiclenumber;
    }

    public void setSch_vehiclenumber(String sch_vehiclenumber) {
        this.sch_vehiclenumber = sch_vehiclenumber;
    }

    public String getSch_time() {
        return sch_time;
    }

    public void setSch_time(String sch_time) {
        this.sch_time = sch_time;
    }

    public String getSch_collegename() {
        return sch_collegename;
    }

    public void setSch_collegename(String sch_collegename) {
        this.sch_collegename = sch_collegename;
    }

    public String getSch_image() {
        return sch_image;
    }

    public void setSch_image(String sch_image) {
        this.sch_image = sch_image;
    }

    public SchoolModelClass(){

    }


    public SchoolModelClass(String sch_name, String sch_email, String sch_phone, String sch_location, String sch_vehicleType, String sch_studentType, String sch_vehiclenumber, String sch_time, String sch_collegename, String sch_image,String sch_desc) {
        this.sch_name = sch_name;
        this.sch_email = sch_email;
        this.sch_phone = sch_phone;
        this.sch_location = sch_location;
        this.sch_vehicleType = sch_vehicleType;
        this.sch_studentType = sch_studentType;
        this.sch_vehiclenumber = sch_vehiclenumber;
        this.sch_time = sch_time;
        this.sch_collegename = sch_collegename;
        this.sch_desc = sch_desc;
        this.sch_image = sch_image;
    }
}
