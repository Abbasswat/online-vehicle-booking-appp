package com.example.bookrides.Model;

public class JobModelClass {
    String jusername,jDatepicker,jPhonenumber,jlocation,jjobDesc,jImage;
//     private String key;
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }

    public String getJusername() {
        return jusername;
    }

    public String getjDatepicker() {
        return jDatepicker;
    }

    public String getjPhonenumber() {
        return jPhonenumber;
    }

    public String getJlocation() {
        return jlocation;
    }

    public String getJjobDesc() {
        return jjobDesc;
    }

    public String getjImage() {
        return jImage;
    }

    public JobModelClass(String jusername, String jDatepicker, String jPhonenumber, String jlocation, String jjobDesc, String jImage) {
        this.jusername = jusername;
        this.jDatepicker = jDatepicker;
        this.jPhonenumber = jPhonenumber;
        this.jlocation = jlocation;
        this.jjobDesc = jjobDesc;
        this.jImage = jImage;
    }
    public JobModelClass(){
    }
}
