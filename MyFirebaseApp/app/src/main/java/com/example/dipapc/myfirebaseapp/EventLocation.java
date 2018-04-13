package com.example.dipapc.myfirebaseapp;

public class EventLocation
{
    private double lat;
    private double lon;
    public EventLocation(){

    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }
}
