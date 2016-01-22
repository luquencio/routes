package com.example.lucascarpio.route.places;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lucascarpio.route.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Fabio Ferreras on 1/3/2016.
 */
public class PlaceList extends android.support.v4.app.Fragment {

    private Context mContext;
    private String mCategory;

    private Place[] mPlaces;
    private ListView mListView;

    public PlaceList() {}

    @SuppressLint("ValidFragment")
    public PlaceList(String category, Context context)
    {
        mContext = context;
        mCategory = category;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mListView = (ListView)view.findViewById(R.id.list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(mContext, PlaceDetail.class);
                i.putExtra("Place", mPlaces[position]);
                startActivity(i);
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Places");
        if(!mCategory.equals("ALL"))
            query.whereEqualTo("categoria", mCategory);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> places, ParseException e) {

                if (e == null)
                {
                    List<Place> placesList = new ArrayList<>();

                    for (int i = 0; i < places.size(); i++)
                        placesList.add(new Place(places.get(i)));


                    Collections.sort(placesList);
                    mPlaces = placesList.toArray(new Place[placesList.size()]);
                    mListView.setAdapter(new PlaceAdapter(mPlaces, mContext));
                }
                else
                {
                    Log.d("PlaceList", e.toString());
                }
            }
        });

        return view;
    }

    private class PlaceAdapter extends ArrayAdapter<Place>
    {
        PlaceAdapter(Place[] places, Context context)
        {
            super(context, R.layout.item_list_row, R.id.item_list_title, places);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView placeName = (TextView)convertView.findViewById(R.id.item_list_title);
            TextView placeAddress = (TextView)convertView.findViewById(R.id.item_list_address);
            ImageView icon = (ImageView)convertView.findViewById(R.id.item_list_icon);

            placeName.setText(mPlaces[position].getName());
            placeAddress.setText(mPlaces[position].getAddress());

            String category = mPlaces[position].getCategory();
            if(category.equals("MUSEO"))
            {
                icon.setImageResource(R.drawable.museum);
            }
            else if (category.equals("RESTAURANTE"))
            {
                icon.setImageResource(R.drawable.restaurant);
            }
            else if (category.equals("TEATRO"))
            {
                icon.setImageResource(R.drawable.theater);
            }
            else if (category.equals("HOTEL"))
            {
                icon.setImageResource(R.drawable.bed);
            }
            else if (category.equals("DISCO"))
            {
                icon.setImageResource(R.drawable.drink);
            }
            else if (category.equals("IGLESIA"))
            {
                icon.setImageResource(R.drawable.church);
            }

            return convertView;
        }
    }

}
