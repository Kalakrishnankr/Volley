package com.goldemo.beachpartner.calendar.myowncalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.goldemo.beachpartner.calendar.compactcalendarview.domain.Event;

import java.util.List;

/**
 * Created by Owner on 2/20/2018.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Event> events;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<Event> events) {
        this.context = applicationContext;
        this.events = events;
        inflter = (LayoutInflater.from(applicationContext));


    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // convertView = inflter.inflate(R.layout.list_view_items, null);
       // TextView textView =(TextView) convertView.findViewById(R.id.textView1);
        //View view =(View) convertView.findViewById(R.id.ribbon);

       // view.setBackgroundColor(events.get(position).getColor());


       // textView.setText(events.get(position).getData().toString());
        return convertView;
    }
}