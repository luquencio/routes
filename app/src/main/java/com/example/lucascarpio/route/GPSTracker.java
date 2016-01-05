package com.example.lucascarpio.route;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Fabio Ferreras on 12/22/2015.
 */
public class GPSTracker extends Service implements LocationListener {

    private Context mContext = null;

    Location mLocation;
    private double mLatitude, mLongitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    protected LocationManager mLocationManager;

    public GPSTracker(Context context)
    {
        mContext = context;
        getLocation();
    }

    public Location getLocation()
    {
        boolean isGPSEnabled;
        boolean isNetworkEnabled;
        try
        {
            mLocationManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled)
            {
                showSettingsAlert();
            }
            else
            {
                if(isNetworkEnabled)
                {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if(mLocationManager != null)
                    {
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(mLocation != null)
                        {
                            mLatitude = mLocation.getLatitude();
                            mLongitude = mLocation.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled)
                {
                    if(mLocation == null)
                    {
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if(mLocationManager != null)
                        {
                            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(mLocation != null)
                            {
                                mLatitude = mLocation.getLatitude();
                                mLongitude = mLocation.getLongitude();
                            }
                        }
                    }
                }

            }
        }
        catch (SecurityException e)
        {
            Log.d("GPS Tracker", e.toString());
        }

        return mLocation;
    }

    public void stopUsingGPS() {
        if(mLocationManager != null) {
            try
            {
                mLocationManager.removeUpdates(GPSTracker.this);
            }
            catch (SecurityException e)
            {
                Log.d("GPS Tracker", e.toString());
            }
        }
    }

    public double getLatitude() {
        if(mLocation != null) {
            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    }

    public double getLongitude() {
        if(mLocation != null) {
            mLongitude = mLocation.getLongitude();
        }

        return mLongitude;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS is settings");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
