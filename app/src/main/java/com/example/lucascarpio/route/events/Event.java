package com.example.lucascarpio.route.events;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by Fabio Ferreras on 1/4/2016.
 */
public class Event implements Comparable<Event>, Serializable{

    private String mName;
    private String mDescription;
    private String mAddress;
    private Date mDate;
    private String mCategory;
    private double mLatitude;
    private double mLongitude;

    Event(ParseObject event)
    {
        mName = event.getString("nombre");
        mDescription = event.getString("descripcion");
        mAddress = event.getString("direccion");
        mDate = event.getDate("fecha");
        mCategory = event.getString("categoria");
        ParseGeoPoint geoPoint = event.getParseGeoPoint("location");
        mLatitude = geoPoint.getLatitude();
        mLongitude = geoPoint.getLongitude();
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAddress() {
        return mAddress;
    }

    public Date getDate() {
        return mDate;
    }

    public String getCategory() {
        return mCategory;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public int compareTo(Event another) {
        return mDate.compareTo(another.mDate);
    }
}
