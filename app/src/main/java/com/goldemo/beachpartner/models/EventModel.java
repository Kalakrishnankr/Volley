package com.goldemo.beachpartner.models;


import java.util.List;

/**
 * Created by seq-kala on 5/3/18.
 */

public class EventModel {
    public List<EventModel> events;
    public String event_color;
    public String event_date;
    public String event_time;

    public EventModel(String event_date, List<EventModel> events) {
    }

    public String getEvent_color() {
        return event_color;
    }

    public void setEvent_color(String event_color) {
        this.event_color = event_color;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_place() {
        return event_place;
    }

    public void setEvent_place(String event_place) {
        this.event_place = event_place;
    }

    public String event_place;

    public EventModel(String event_color,String event_date, String event_time, String event_place) {
        this.event_color= event_color;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_place = event_place;
    }
    public List<EventModel> getEvents() {
        return events;
    }
}
