package com.example.lucascarpio.route.places;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lucascarpio.route.LoginActivity;
import com.example.lucascarpio.route.MainActivity;
import com.example.lucascarpio.route.R;
import com.example.lucascarpio.route.routes.RouteActivity;
import com.example.lucascarpio.route.events.EventsActivity;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Toolbar toolbar = (Toolbar)findViewById(R.id.places_toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.places_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.places_tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.places_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.places_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.places);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        /*
            Adding tabs
            PlaceList -> Fragment
               String -> Tab Name
         */
        adapter.addFragment(new PlaceList("ALL", this), "TODOS");
        adapter.addFragment(new PlaceList("MUSEO", this), "MUSEOS");
        adapter.addFragment(new PlaceList("RESTAURANTE", this), "RESTAURANTES");
        adapter.addFragment(new PlaceList("TEATRO", this), "TEATROS");
        adapter.addFragment(new PlaceList("HOTEL", this), "HOTEL");
        adapter.addFragment(new PlaceList("DISCO", this), "DISCO");
        adapter.addFragment(new PlaceList("IGLESIA", this), "IGLESIA");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.main)
        {
            Intent intent = new Intent(PlacesActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.events)
        {
            Intent intent = new Intent(PlacesActivity.this,EventsActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.routes)
        {
            Intent intent = new Intent(PlacesActivity.this,RouteActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.log_out)
        {
            ParseUser.logOut();
            Intent intent = new Intent(PlacesActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.places_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlacesActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
