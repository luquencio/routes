package com.example.lucascarpio.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lucascarpio.route.events.EventsActivity;
import com.example.lucascarpio.route.places.PlacesActivity;
import com.example.lucascarpio.route.routes.RouteActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PlacesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            // Send user to LoginActivity.class
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        GPSTracker gpsTracker = new GPSTracker(this);
        gpsTracker.getLocation();

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("You're here")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_position)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));

        checkLocation(latitude, longitude);
    }


    //Check if the current position is inside the area of the Colonial Zone
    public void checkLocation(double latitude, double longitude) {

        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if(addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String subLocality = address.getSubLocality();
                if(!subLocality.equals("Ciudad Colonial"))
                {
                    dialogZCNotFound().show();
                }
            }
            else
            {
                Toast.makeText(this, "Can't get your current location", Toast.LENGTH_LONG).show();
            }


        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }

    public AlertDialog dialogZCNotFound()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Fuera de la Zona Colonial")
                .setMessage("Actualmente no te encuentras en la Zona Colonial. " +
                        "Si deseas una ruta para ir a la Zona Colonial pulsa el boton de abajo.")
                .setPositiveButton("Ir a la Zona Colonial", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                            Open Google Maps and make a route from the current position
                            to a hardcoded place inside the Colonial Zone
                         */
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=18.477451,-69.882721");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        finish();
                    }
                })
                .setNegativeButton("No, gracias", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                });

        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.places) {
            Intent intent = new Intent(MainActivity.this,PlacesActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.events) {
            Intent intent = new Intent(MainActivity.this,EventsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.routes) {
            Intent intent = new Intent(MainActivity.this,RouteActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.log_out) {
            ParseUser.logOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
