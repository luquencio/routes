package com.example.lucascarpio.route;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lucascarpio.route.events.EventsActivity;
import com.example.lucascarpio.route.places.PlacesActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public List<LatLng> SaturdayRoute;
    public List<LatLng> MuseumRoute;
    public List<LatLng> ChurchRoute;

    private ListView myListView;

    private Route[] mRoutes;


    //THAT THE HARDCODE BEGIN!
    public void setSaturdayRoute(List<LatLng> route) {
        route.add(new LatLng(18.47157903, -69.89128187));
        route.add(new LatLng(18.47388613, -69.88360774));
        route.add(new LatLng(18.47233079, -69.8832956));
        route.add(new LatLng(18.47260904, -69.88252312));
        route.add(new LatLng(18.47552095, -69.88294691));

        SaturdayRoute = route;
    }


    public void setMuseumRoute(List<LatLng> route){
        route.add(new LatLng(18.477733,-69.8843144));
        route.add(new LatLng(18.477451,-69.88272));
        route.add(new LatLng(18.4759193,-69.8832859));
        route.add(new LatLng(18.47516, -69.8830018));
        route.add(new LatLng(18.4732, -69.88171));

        MuseumRoute = route;
    }

    public void setChurchRoute(List<LatLng> route) {

        route.add(new LatLng(18.4755489,-69.8827082));
        route.add(new LatLng(18.4752527,-69.8855812));
        route.add(new LatLng(18.4734974,-69.888422));
        route.add(new LatLng(18.4715111,-69.8887834));
        route.add(new LatLng(18.4706267,-69.887055));
        route.add(new LatLng(18.4722526,-69.8833932));

        ChurchRoute = route;
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

        //ImageView image = (ImageView)findViewById(R.id.route_row_image);
        //Picasso.with(this).load();

        // ADDING ROUTE #1
        List<Route> RouteList = new ArrayList<>();
        RouteList.add(new Route("Saturday Nights", "Enjoy the best places for hangout on La Zona Colonial.", "Blablabla", SaturdayRoute));
        RouteList.add(new Route("Museum Route", "Take a look for a important cultural places on La Zona Colonial.","", MuseumRoute));
        RouteList.add(new Route("Church Routes","See over uppon Catholic places. \nIdeal for Sundays and Family trips.","",ChurchRoute));
        mRoutes = RouteList.toArray(new Route[RouteList.size()]);
        myListView.setAdapter(new ListAdapter(mRoutes));

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(RouteActivity.this, RouteDetail.class);
                intent.putExtra("Route", mRoutes[position]);
                startActivity(intent);

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


}
