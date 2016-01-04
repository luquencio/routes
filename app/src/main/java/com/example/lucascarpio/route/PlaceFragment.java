package com.example.lucascarpio.route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabio Ferreras on 1/3/2016.
 */
public class PlaceFragment extends android.support.v4.app.Fragment {

    private Context mContext;
    private String mCategory;

    private Place[] mPlaces;
    private ListView mListView;

    public PlaceFragment() {}

    @SuppressLint("ValidFragment")
    public PlaceFragment(String category, Context context)
    {
        mContext = context;
        mCategory = category;
        Log.d("PlaceFragment", "PlaceFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PlaceFragment", "onCreate");
        mPlaces = new Place[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("PlaceFragment", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_place, container, false);

        mListView = (ListView)view.findViewById(R.id.list);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Places");
        if(!mCategory.equals("ALL"))
            query.whereEqualTo("categoria", mCategory);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> places, ParseException e) {
                Log.d("PlaceFragment", "done");
                if (e == null) {
                    List<Place> placesList = new ArrayList<>();

                    for (int i = 0; i < places.size(); i++) {
                        placesList.add(new Place(places.get(i)));
                    }

                    mPlaces = placesList.toArray(new Place[placesList.size()]);
                    mListView.setAdapter(new PlaceAdapter(mPlaces, mContext));
                } else {
                    Log.d("PlaceFragment", e.toString());
                }
            }
        });

        mListView.setAdapter(new PlaceAdapter(mPlaces, mContext));

        return view;
    }

    private class PlaceAdapter extends ArrayAdapter<Place>
    {
        PlaceAdapter(Place[] places, Context context)
        {
            super(context, android.R.layout.simple_list_item_1, places);
        }
    }

}
