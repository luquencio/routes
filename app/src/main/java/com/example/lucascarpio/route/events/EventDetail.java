package com.example.lucascarpio.route.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.lucascarpio.route.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventDetail extends AppCompatActivity implements OnMapReadyCallback{

    Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        mEvent = (Event)getIntent().getSerializableExtra("Event");

        TextView placeTitle = (TextView)findViewById(R.id.event_detail_title);
        placeTitle.setText(mEvent.getName());

        TextView placeAddress = (TextView) findViewById(R.id.event_detail_address);
        placeAddress.setText(mEvent.getAddress());

        TextView placeDescription = (TextView) findViewById(R.id.event_detail_description);
        placeDescription.setText(mEvent.getDescription());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_event);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_event_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Open Google Maps and make a route from the current position
                    to event's address.
                 */
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + mEvent.getLatitude() + "," +
                        mEvent.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        double latitude = mEvent.getLatitude();
        double longitude = mEvent.getLongitude();

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(mEvent.getName()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }
}
