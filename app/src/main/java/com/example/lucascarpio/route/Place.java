package com.example.lucascarpio.route;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by Fabio Ferreras on 1/3/2016.
 */
public class Place {

    private String mName;
    private String mDescription;
    private String mCategory;
    private String mAddress;
    private double mLatitude;
    private double mLongitude;

    Place(ParseObject place)
    {
        mName = place.getString("nombre");
        mDescription = place.getString("descripcion");
        mCategory = place.getString("categoria");
        mAddress = place.getString("direccion");
        ParseGeoPoint geoPoint = place.getParseGeoPoint("location");
        mLatitude = geoPoint.getLatitude();
        mLongitude = geoPoint.getLongitude();
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getAddress() {
        return mAddress;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public String toString() {
        return mName;
    }
}
