package com.example.lucascarpio.route.places;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Toolbar toolbar = (Toolbar)findViewById(R.id.places_toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager)findViewById(R.id.places_viewpager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.places_tabs);
        tabLayout.setupWithViewPager(mViewPager);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            setupViewPager(mViewPager);

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = this.getCurrentFocus();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            isSearchOpened = false;
        } else {
            //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edit_search); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            isSearchOpened = true;
        }
    }

    private void doSearch()
    {
        String query = edtSeach.getText().toString();
//        handleMenuSearch();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        View view = this.getCurrentFocus();
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PlaceList("ALL", this, query), "TODOS");
        adapter.addFragment(new PlaceList("MUSEO", this), "MUSEOS");
        adapter.addFragment(new PlaceList("RESTAURANTE", this), "RESTAURANTES");
        adapter.addFragment(new PlaceList("TEATRO", this), "TEATROS");
        adapter.addFragment(new PlaceList("HOTEL", this), "HOTEL");
        adapter.addFragment(new PlaceList("DISCO", this), "DISCO");
        adapter.addFragment(new PlaceList("IGLESIA", this), "IGLESIA");
        mViewPager.setAdapter(adapter);


    }
}
