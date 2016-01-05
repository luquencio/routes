package com.example.lucascarpio.route.events;

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
import com.example.lucascarpio.route.places.PlacesActivity;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = (Toolbar)findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.events_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.events_tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.events_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.events_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.events);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        /*
            Adding tabs
            EventsList -> Fragment
               String -> Tab Name
         */
        adapter.addFragment(new EventsList("ALL", this), "TODOS");
        adapter.addFragment(new EventsList("MUSICAL", this), "MUSICALES");
        adapter.addFragment(new EventsList("TEATRO", this), "TEATROS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.main)
        {
            Intent intent = new Intent(EventsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.places)
        {
            Intent intent = new Intent(EventsActivity.this,PlacesActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.routes)
        {
            //Move to Route's View
        }
        else if (id == R.id.log_out)
        {
            ParseUser.logOut();
            Intent intent = new Intent(EventsActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.events_drawer_layout);
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
        Intent intent = new Intent(EventsActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
