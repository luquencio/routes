package com.example.lucascarpio.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lucascarpio.route.events.EventsActivity;
import com.example.lucascarpio.route.places.Place;
import com.example.lucascarpio.route.places.PlacesActivity;
import com.example.lucascarpio.route.routes.RouteActivity;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;

    private static final String MUSEUM_CATEGORY = "MUSEO";
    private static final String RESTAURANT_CATEGORY = "RESTAURANTE";
    private static final String THEATER_CATEGORY = "TEATRO";
    private static final String HOTEL_CATEGORY = "HOTEL";
    private static final String BAR_CATEGORY = "DISCO";
    private static final String CHURCH_CATEGORY = "IGLESIA";

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Admob specs
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");



        //Floating Button Settings
        int subActionButtonSize = getResources().getDimensionPixelSize(R.dimen.sub_action_button_size);
        int subActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.sub_action_button_content_margin);

        FrameLayout.LayoutParams subButtonParams = new FrameLayout.LayoutParams(subActionButtonSize, subActionButtonSize);
        FrameLayout.LayoutParams marginParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        marginParams.setMargins(subActionButtonContentMargin,
                subActionButtonContentMargin,
                subActionButtonContentMargin,
                subActionButtonContentMargin);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setLayoutParams(marginParams);
        itemBuilder.setLayoutParams(subButtonParams);

        //Floating Button Icons
        ImageView museumIcon = new ImageView(this);
        museumIcon.setImageResource(R.drawable.museum);

        ImageView restaurantIcon = new ImageView(this);
        restaurantIcon.setImageResource(R.drawable.restaurant);

        ImageView theaterIcon = new ImageView(this);
        theaterIcon.setImageResource(R.drawable.theater);

        ImageView hotelIcon = new ImageView(this);
        hotelIcon.setImageResource(R.drawable.bed);

        ImageView barIcon = new ImageView(this);
        barIcon.setImageResource(R.drawable.drink);

        ImageView churchIcon = new ImageView(this);
        churchIcon.setImageResource(R.drawable.church);

        //Floating Buttons
        ImageView searchIcon = new ImageView(this);
        searchIcon.setImageResource(android.R.drawable.ic_menu_search);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(searchIcon)
                .build();

        SubActionButton museumItem = itemBuilder.setContentView(museumIcon).build();
        museumItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIconOnMap(MUSEUM_CATEGORY);
            }
        });

        SubActionButton restaurantItem = itemBuilder.setContentView(restaurantIcon).build();
        restaurantItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIconOnMap(RESTAURANT_CATEGORY);
            }
        });

        SubActionButton theatherItem = itemBuilder.setContentView(theaterIcon).build();
        theatherItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIconOnMap(THEATER_CATEGORY);
            }
        });

        SubActionButton hotelItem = itemBuilder.setContentView(hotelIcon).build();
        hotelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIconOnMap(HOTEL_CATEGORY);
            }
        });

        SubActionButton barItem = itemBuilder.setContentView(barIcon).build();
        barItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIconOnMap(BAR_CATEGORY);
            }
        });

        SubActionButton churchItem = itemBuilder.setContentView(churchIcon).build();
        churchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIconOnMap(CHURCH_CATEGORY);
            }
        });

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(museumItem)
                .addSubActionView(restaurantItem)
                .addSubActionView(theatherItem)
                .addSubActionView(hotelItem)
                .addSubActionView(barItem)
                .addSubActionView(churchItem)
                .attachTo(actionButton)
                .build();


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
        mGoogleMap = googleMap;
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

    private void loadIconOnMap(final String category)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Places");
        query.whereEqualTo("categoria", category);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> places, ParseException e) {
                if(e == null)
                {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_search);

                    if(category == MUSEUM_CATEGORY)
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.museum);
                    if(category == RESTAURANT_CATEGORY)
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.restaurant);
                    if(category == THEATER_CATEGORY)
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.theater);
                    if(category == HOTEL_CATEGORY)
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.bed);
                    if(category == BAR_CATEGORY)
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.drink);
                    if(category == CHURCH_CATEGORY)
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.church);

                    mGoogleMap.clear();

                    GPSTracker gpsTracker = new GPSTracker(MainActivity.this);
                    gpsTracker.getLocation();

                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title("You're here")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_position)));

                    for (int i = 0; i < places.size(); i++) {
                        Place place = new Place(places.get(i));
                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(place.getLatitude(), place.getLongitude()))
                                .title(place.getName())
                                .icon(icon));
                    }
                }
            }
        });
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
