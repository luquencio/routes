package com.example.lucascarpio.route;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.*;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucascarpio.route.events.Event;
import com.example.lucascarpio.route.events.EventDetail;
import com.example.lucascarpio.route.events.EventsActivity;
import com.example.lucascarpio.route.places.PlacesActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RouteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public List<LatLng> Route1;
    public List<LatLng> Route2;
    public List<LatLng> Route3;

    private ListView myListView;

    private Route[] mRoutes;


    //THAT THE HARDCODE BEGIN!
    public void setRoute1(List<LatLng> route1) {
        route1.add(new LatLng(18.47157903, -69.89128187)); //Inicio Conde
        route1.add(new LatLng(18.47388613, -69.88360774)); //Fin Conde (Parque Colon)
        route1.add(new LatLng(18.47233079, -69.8832956)); //Catedral
        route1.add(new LatLng(18.47260904, -69.88252312)); //Esquina luego de Catedral
        route1.add(new LatLng(18.47552095, -69.88294691)); //Alcazar de Colon

        Route1 = route1;
    }

    public void setRoute2(List<LatLng> route2) {
        Route2 = route2;
    }

    public void setRoute3(List<LatLng> route3) {
        Route3 = route3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar = (Toolbar) findViewById(R.id.route_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.route_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.route_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.routes);

        myListView = (ListView)findViewById(R.id.route_list);

        // ADDING ROUTE #1
        List<Route> RouteList = new ArrayList<>();
        RouteList.add(new Route("Ruta #1", "Blablabla", "Blablabla 2", Route1));
        mRoutes = RouteList.toArray(new Route[RouteList.size()]);
        myListView.setAdapter(new ListAdapter(mRoutes));

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Intent intent = new Intent(RouteActivity.this, RouteDetail.class);
                //intent.putExtra("Route", mRoutes[position]);
                //startActivity(intent);

            }
        });
    }

    private class ListAdapter extends ArrayAdapter<Route> {

        ListAdapter(Route[] routes)
        {
            super(RouteActivity.this, R.layout.route_list_row, R.id.route_row_title, routes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView textTitle = (TextView)convertView.findViewById(R.id.route_row_title);
            TextView textDescription = (TextView)convertView.findViewById(R.id.route_row_description);

            textTitle.setText(mRoutes[position].getName());
            textDescription.setText(mRoutes[position].getDescription());

            return convertView;
        }
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
            Intent intent = new Intent(RouteActivity.this,PlacesActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.events) {
            Intent intent = new Intent(RouteActivity.this,EventsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.log_out) {
            ParseUser.logOut();
            Intent intent = new Intent(RouteActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.route_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public PolylineOptions makePolygon(List<LatLng> Route) {

        PolylineOptions Drawler = new PolylineOptions();
        Drawler.addAll(Route);
        Drawler.width(9);
        Drawler.color(R.color.colorPrimaryDark);

        return Drawler;
    }

}
