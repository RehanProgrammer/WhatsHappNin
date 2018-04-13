package com.example.dipapc.myfirebaseapp;

/**
 * Created by DipaPc on 4/10/2018.
 */

public class Event {
    private String name;
    private String description;
    private String photourl;
    private String loaction;
    private double lat;
    private double lon;


    public Event(){

    }
    public Event(String name, String description, String photourl,String location,double lat,double lon){
        this.name = name;
        this.description = description;
        this.photourl = photourl;
        this.loaction = location;
        this.lat = lat;
        this.lon = lon;




    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction;
    }

    public String getLoaction() {
        return loaction;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
