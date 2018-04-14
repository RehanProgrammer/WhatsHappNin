package com.example.dipapc.myfirebaseapp;

/**
 * Created by DipaPc on 4/10/2018.
 */

public class Event {
    private String name;
    private String description;
    private String photourl;
    private String loaction;
    private String eventdate;
    private String eventtime;
    private String eventtype;
    private int guetNo;
    private double lat;
    private double lon;


    public Event(){

    }
    public Event(String name, String description, String photourl,String location,double lat,double lon,String eventdate,
                 String eventtime,int guetNo,String eventtype){
        this.name = name;
        this.description = description;
        this.photourl = photourl;
        this.loaction = location;
        this.lat = lat;
        this.lon = lon;
        this.eventdate = eventdate;
        this.eventtime = eventtime;
        this.guetNo = guetNo;
        this.eventtype = eventtype;




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

    public int getGuetNo() {
        return guetNo;
    }

    public void setGuetNo(int guetNo) {
        this.guetNo = guetNo;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }
}
