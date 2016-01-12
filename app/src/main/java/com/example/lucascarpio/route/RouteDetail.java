package com.example.lucascarpio.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lucascarpio.route.events.Event;
import com.example.lucascarpio.route.places.PlacesActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;

/**
 * Created by lucasecarpio on 1/10/16.
 */
public class RouteDetail extends AppCompatActivity implements OnMapReadyCallback  {

    SupportMapFragment mapFragment;

    Route mmRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_detail_activity);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mmRoute = (Route)getIntent().getSerializableExtra("Route");




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GPSTracker gpsTracker = new GPSTracker(this);
        gpsTracker.getLocation();

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("You're here"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));

    }


}
