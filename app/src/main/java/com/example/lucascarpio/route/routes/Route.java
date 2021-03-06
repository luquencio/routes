package com.example.lucascarpio.route.routes;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lucasecarpio on 1/10/16.
 */
public class Route implements Serializable{

    String mName;
    String mDescription;
    int mUrl;
    List<LatLng> mLocations;


    Route(String Name, String Description, int Url, List<LatLng> locations) {
        mName = Name;
        mDescription = Description;
        mUrl = Url;
        mLocations = locations;
    }


    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getUrl() {
        return mUrl;
    }

    public List<LatLng> getLocations() {
        return mLocations;
    }
}
