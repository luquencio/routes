package com.example.lucascarpio.route;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;


/**
 * Created by Fabio Ferreras on 12/18/2015.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "5bANf1cB9OlIBR1QoERP3Z9ZOjalZIPmRddKgYGz", "mq4ESbOw5aB9rDpqVn9DiGafnyIBnKm0pwbqEIBI");

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}
