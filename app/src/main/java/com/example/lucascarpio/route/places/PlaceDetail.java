package com.example.lucascarpio.route.places;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucascarpio.route.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class PlaceDetail extends AppCompatActivity implements OnMapReadyCallback {

    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        mPlace = (Place)getIntent().getSerializableExtra("Place");

        TextView placeTitle = (TextView)findViewById(R.id.place_detail_title);
        placeTitle.setText(mPlace.getName());

        TextView placeDescription = (TextView) findViewById(R.id.place_detail_description);
        placeDescription.setText(mPlace.getDescription());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_place);
        mapFragment.getMapAsync(this);

        ImageView image = (ImageView)findViewById(R.id.place_detail_image);
        Picasso.with(this).load(mPlace.getImageURL()).into(image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_place_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Open Google Maps and make a route from the current position
                    to place's address.
                 */
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+mPlace.getLatitude()+"," +
                        mPlace.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        double latitude = mPlace.getLatitude();
        double longitude = mPlace.getLongitude();

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(mPlace.getName()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }
}
