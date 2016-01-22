package com.example.lucascarpio.route;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

/**
 * Created by lucasecarpio on 1/10/16.
 */
public class RouteDetail extends Activity{

    Route mRoute;

    WebView mMyBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);

        mMyBrowser = (WebView)findViewById(R.id.view_route);


        if (mRoute.getName()=="SaturdayRoute"){
            mMyBrowser.loadUrl("file:///android_asset/SaturdayRoutes.html");
        }
        if (mRoute.getName()=="MuseumRoute"){
            mMyBrowser.loadUrl("file:///android_asset/MuseumRoutes.html");
        }
        if (mRoute.getName()=="ChurchRoute"){
            mMyBrowser.loadUrl("file:///android_asset/ChurchesRoutes.html");
        }
        if (mRoute.getName()=="CustomRoute"){
            mMyBrowser.loadUrl("file:///android_asset/CustomRoutes.html");
        }

        mMyBrowser.getSettings().setJavaScriptEnabled(true);

    }



}
