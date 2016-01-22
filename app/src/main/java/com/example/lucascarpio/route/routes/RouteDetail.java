package com.example.lucascarpio.route.routes;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.lucascarpio.route.R;

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
        mMyBrowser.getSettings().setJavaScriptEnabled(true);

        mMyBrowser.setWebChromeClient(new WebChromeClient(){
           public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
               // callback.invoke(String origin, boolean allow, boolean remember);
               callback.invoke(origin, true, false);
           }
       });

        mRoute = (Route)getIntent().getSerializableExtra("Route");

        if (mRoute.getName().equals("Saturday Nights")){
            mMyBrowser.loadUrl("file:///android_asset/SaturdayRoutes.html");
        }
        if (mRoute.getName().equals("Museum Route")){
            mMyBrowser.loadUrl("file:///android_asset/MuseumRoutes.html");
        }
        if (mRoute.getName().equals("Church Routes")){
            mMyBrowser.loadUrl("file:///android_asset/ChurchesRoutes.html");
        }
        if (mRoute.getName().equals("Custom Routes")){
            mMyBrowser.loadUrl("file:///android_asset/CustomRoutes.html");
        }
    }



}
