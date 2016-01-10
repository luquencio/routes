package com.example.lucascarpio.route;

/**
 * Created by lucasecarpio on 1/10/16.
 */
public class Route {

    String mName;
    String mDescription;
    String mUrl;


    Route(String Name, String Description, String Url) {
        mName = Name;
        mDescription = Description;
        mUrl = Url;
    }


    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmUrl() {
        return mUrl;
    }
}
