package com.example.quakereport;

public class EarthQuake {
    private double magnitude;
    private String city;
    private Long mTimeInMilliSec;
    private String mUrl;

    public EarthQuake(double magnitude, String city, Long date, String url){
        this.magnitude = magnitude;
        this.city = city;
        this.mTimeInMilliSec = date;
        this.mUrl = url;
    }

    public double getMagnitude(){
        return magnitude;
    }
    public String getCity(){
        return city;
    }
    public String getUrl(){return mUrl;}
    public Long getTimeInMilliSec() {
        return mTimeInMilliSec;
    }
}
