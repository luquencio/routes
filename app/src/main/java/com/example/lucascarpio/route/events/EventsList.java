package com.example.lucascarpio.route.events;

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
import android.widget.ListView;
import android.widget.TextView;

import com.example.lucascarpio.route.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fabio Ferreras on 1/3/2016.
 */
public class EventsList extends android.support.v4.app.Fragment {

    private Context mContext;
    private String mCategory;

    private Event[] mEvents;
    private ListView mListView;

    public EventsList() {}

    @SuppressLint("ValidFragment")
    public EventsList(String category, Context context)
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
                Intent i = new Intent(mContext, EventDetail.class);
                i.putExtra("Event", mEvents[position]);
                startActivity(i);
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        if(!mCategory.equals("ALL"))
            query.whereEqualTo("categoria", mCategory);

        query.whereGreaterThanOrEqualTo("fecha", new Date());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> event, ParseException e) {
                if (e == null)
                {
                    List<Event> eventsList = new ArrayList<>();

                    for (int i = 0; i < event.size(); i++)
                        eventsList.add(new Event(event.get(i)));

                    Collections.sort(eventsList);
                    mEvents = eventsList.toArray(new Event[eventsList.size()]);
                    mListView.setAdapter(new PlaceAdapter(mEvents, mContext));
                }
                else
                {
                    Log.d("PlaceList", e.toString());
                }
            }
        });

        return view;
    }

    private class PlaceAdapter extends ArrayAdapter<Event>
    {
        PlaceAdapter(Event[] places, Context context)
        {
            super(context, R.layout.item_list_row, R.id.item_list_title, places);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView placeName = (TextView)convertView.findViewById(R.id.item_list_title);
            TextView placeDate = (TextView)convertView.findViewById(R.id.item_list_address);

            placeName.setText(mEvents[position].getName());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            placeDate.setText(dateFormat.format(mEvents[position].getDate()));

            return convertView;
        }
    }

}
